package com.jeecms.bbs.manager.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeecms.bbs.dao.BbsUserDao;
import com.jeecms.bbs.entity.BbsCommonMagic;
import com.jeecms.bbs.entity.BbsMemberMagic;
import com.jeecms.bbs.entity.BbsUser;
import com.jeecms.bbs.entity.BbsUserActiveLevel;
import com.jeecms.bbs.entity.BbsUserExt;
import com.jeecms.bbs.entity.BbsUserGroup;
import com.jeecms.bbs.entity.BbsUserOnline;
import com.jeecms.bbs.manager.BbsCommonMagicMng;
import com.jeecms.bbs.manager.BbsMemberMagicMng;
import com.jeecms.bbs.manager.BbsUserActiveLevelMng;
import com.jeecms.bbs.manager.BbsUserExtMng;
import com.jeecms.bbs.manager.BbsUserGroupMng;
import com.jeecms.bbs.manager.BbsUserMng;
import com.jeecms.bbs.manager.BbsUserOnlineMng;
import com.jeecms.common.email.EmailSender;
import com.jeecms.common.email.MessageTemplate;
import com.jeecms.common.hibernate3.Updater;
import com.jeecms.common.page.Pagination;
import com.jeecms.core.entity.UnifiedUser;
import com.jeecms.core.manager.CmsConfigMng;
import com.jeecms.core.manager.UnifiedUserMng;

@Service
@Transactional
public class BbsUserMngImpl implements BbsUserMng {
	
	public List<BbsUser> getList(String username, Integer count){
		return dao.getList(username, count);
	}

	@Transactional(readOnly = true)
	public Pagination getPage(String username, String email, Integer groupId,
			Boolean disabled, Boolean admin,Boolean official,Integer lastLoginDay,Integer orderBy, int pageNo,
			int pageSize) {
		Pagination page = dao.getPage(username, email, groupId, disabled,admin,official,lastLoginDay, orderBy,pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public List<BbsUser> getList(Integer count) {
		return dao.getList(count);
	}
	
	@Transactional(readOnly = true)
	public List<BbsUser> getAdminList(Integer siteId, Boolean allChannel,
			Boolean disabled, Integer rank) {
		return dao.getAdminList(siteId, allChannel, disabled, rank);
	}

	@Transactional(readOnly = true)
	public BbsUser findById(Integer id) {
		BbsUser entity = dao.findById(id);
		return entity;
	}

	@Transactional(readOnly = true)
	public BbsUser findByUsername(String username) {
		BbsUser  entity = null;
		if((!StringUtils.isBlank(username))){
			entity = dao.findByUsername(username);
		}
		return entity;
	}

	public BbsUser registerMember(String username, String email,Boolean official,
			String password, String ip, Integer groupId, BbsUserExt userExt,Map<String,String>attr) throws UnsupportedEncodingException, MessagingException {
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
				password, ip);
		BbsUser user = new BbsUser();
		user.forMember(unifiedUser);
		user.setAttr(attr);
		BbsUserGroup group = null;
		if (groupId != null) {
			group = bbsUserGroupMng.findById(groupId);
		} else {
			group = bbsUserGroupMng.getRegDef();
		}
		if (group == null) {
			throw new RuntimeException(
					"register default member group not found!");
		}
		Integer defaultActiveLevel=cmsConfigMng.get().getDefaultActiveLevel();
		user.setActiveLevel(bbsUserActiveLevelMng.findById(defaultActiveLevel));
		if(official!=null){
			user.setOfficial(official);
		}else{
			user.setOfficial(false);
		}
		user.setGroup(group);
		user.init();
		user=dao.save(user);
		bbsUserExtMng.save(userExt, user);
		bbsUserOnlineMng.saveByUser(user);
		return user;
	}

	public BbsUser registerMember(String username, String email,
			String password, String ip, Integer groupId, BbsUserExt userExt,Map<String,String>attr,
			Boolean activation, EmailSender sender, MessageTemplate msgTpl) throws UnsupportedEncodingException, MessagingException {
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
				password, ip, activation, sender, msgTpl);
		BbsUser user = new BbsUser();
		user.forMember(unifiedUser);
		user.setAttr(attr);
		BbsUserGroup group = null;
		if (groupId != null) {
			group = bbsUserGroupMng.findById(groupId);
		} else {
			group = bbsUserGroupMng.getRegDef();
		}
		if (group == null) {
			throw new RuntimeException(
					"register default member group not found!");
		}
		Integer defaultActiveLevel=cmsConfigMng.get().getDefaultActiveLevel();
		user.setActiveLevel(bbsUserActiveLevelMng.findById(defaultActiveLevel));
		user.setGroup(group);
		user.init();
		user=dao.save(user);
		bbsUserExtMng.save(userExt, user);
		return user;
	}

	public void updateLoginInfo(Integer userId, String ip,Date loginTime,String sessionId) {
		BbsUser user = findById(userId);
		if (user != null) {
			user.setLoginCount(user.getLoginCount() + 1);
			if(StringUtils.isNotBlank(ip)){
				user.setLastLoginIp(ip);
			}
			if(loginTime!=null){
				user.setLastLoginTime(loginTime);
			}
			user.setSessionId(sessionId);
		}
	}

	public void updateUploadSize(Integer userId, Integer size) {
		BbsUser user = findById(userId);
		user.setUploadTotal(user.getUploadTotal() + size);
		if (user.getUploadDate() != null) {
			if (BbsUser.isToday(user.getUploadDate())) {
				size += user.getUploadSize();
			}
		}
		user.setUploadDate(new java.sql.Date(System.currentTimeMillis()));
		user.setUploadSize(size);
	}

	public boolean isPasswordValid(Integer id, String password) {
		return unifiedUserMng.isPasswordValid(id, password);
	}

	public void updatePwdEmail(Integer id, String password, String email) {
		BbsUser user = findById(id);
		if (!StringUtils.isBlank(email)) {
			user.setEmail(email);
		} else {
			user.setEmail(null);
		}
		unifiedUserMng.update(id, password, email);
	}
	
	public void updateGroup(Integer id, Integer groupId){
		BbsUser user = findById(id);
		user.setGroup(bbsUserGroupMng.findById(groupId));
	}

	public BbsUser saveAdmin(String username, String email, String password,
			String ip, boolean viewOnly, boolean selfAdmin, int rank,
			Integer groupId, Integer[] roleIds, Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, Boolean[] allChannels,
			BbsUserExt userExt) throws UnsupportedEncodingException, MessagingException {
		UnifiedUser unifiedUser = unifiedUserMng.save(username, email,
				password, ip);
		BbsUser user = new BbsUser();
		user.forAdmin(unifiedUser, viewOnly, selfAdmin, rank);
		BbsUserGroup group = null;
		if (groupId != null) {
			group = bbsUserGroupMng.findById(groupId);
		} else {
			group = bbsUserGroupMng.getRegDef();
		}
		if (group == null) {
			throw new RuntimeException(
					"register default member group not setted!");
		}
		Integer defaultActiveLevel=cmsConfigMng.get().getDefaultActiveLevel();
		user.setActiveLevel(bbsUserActiveLevelMng.findById(defaultActiveLevel));
		user.setGroup(group);
		user.init();
		dao.save(user);
		bbsUserExtMng.save(userExt, user);
		return user;
	}

	public BbsUser updateAdmin(BbsUser bean, BbsUserExt ext, String password,
			Integer groupId, Integer[] roleIds, Integer[] channelIds,
			Integer[] siteIds, Byte[] steps, Boolean[] allChannels) {
		Updater<BbsUser> updater = new Updater<BbsUser>(bean);
		updater.include("email");
		BbsUser user = dao.updateByUpdater(updater);
		user.setGroup(bbsUserGroupMng.findById(groupId));
		bbsUserExtMng.update(ext, user);
		unifiedUserMng.update(bean.getId(), password, bean.getEmail());
		return user;
	}

	public BbsUser updateMember(Integer id, String email, String password,
			Boolean isDisabled, String signed, String avatar, BbsUserExt ext,Map<String,String>attr,
			Integer groupId) {
		BbsUser entity = findById(id);
		if (!StringUtils.isBlank(email)) {
			entity.setEmail(email);
		}
		if (isDisabled != null) {
			entity.setDisabled(isDisabled);
		}
		if (signed != null) {
			entity.setSigned(signed);
		}
		if (avatar != null) {
			entity.setAvatar(avatar);
		}
		if (groupId != null) {
			entity.setGroup(bbsUserGroupMng.findById(groupId));
		}
		// 更新属性表
		if (attr != null) {
			Map<String, String> attrOrig = entity.getAttr();
			attrOrig.clear();
			attrOrig.putAll(attr);
		}
		bbsUserExtMng.update(ext, entity);
		unifiedUserMng.update(id, password, email);
		return entity;
	}
	
	public BbsUser updateMember(Integer id, String email, String password,String realname,Boolean gender,String tel){
		BbsUser entity = findById(id);
		if (!StringUtils.isBlank(email)) {
			entity.setEmail(email);
		}
		BbsUserExt ext = entity.getUserExt();
		ext.setRealname(realname);
		ext.setGender(gender);
		ext.setMoble(tel);
		bbsUserExtMng.update(ext, entity);
		unifiedUserMng.update(id, password, email);
		return entity;
	}

	public BbsUser deleteById(Integer id) {
		unifiedUserMng.deleteById(id);
		BbsUser bean = dao.deleteById(id);
		return bean;
	}

	public BbsUser[] deleteByIds(Integer[] ids) {
		BbsUser[] beans = new BbsUser[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	public boolean usernameNotExist(String username) {
		return dao.countByUsername(username) <= 0;
	}

	public boolean emailNotExist(String email) {
		return dao.countByEmail(email) <= 0;
	}

	@Transactional(readOnly = true)
	public List<BbsUser> getSuggestMember(String username, Integer count) {
		return dao.getSuggestMember(username, count);
	}
	
	public void updateActiveLevel(){
		List<BbsUserActiveLevel>levels=bbsUserActiveLevelMng.getList(Integer.MAX_VALUE);
		List<BbsUser>users=getList(Integer.MAX_VALUE);
		for(BbsUser u:users){
			BbsUserActiveLevel l=getUpdateToLevel(u, levels);
			dao.updateActiveLevel(u, l);
		}
	}
	
	private BbsUserActiveLevel getUpdateToLevel(BbsUser user,List<BbsUserActiveLevel>levels){
		if(levels!=null&&levels.size()>0){
			BbsUserActiveLevel level=levels.get(0);
			for(BbsUserActiveLevel l:levels){
				BbsUserOnline online=user.getUserOnline();
				if(online!=null){
					if(online.getOnlineTotal()>l.getRequiredHour()){
						level=l;
						continue;
					}else{
						break;
					}
				}
			}
			return level;
		}else{
			return	null;
		}
	}

	public void updatePoint(Integer userId, Integer point, Integer prestige,
			String mid, int num, int operator) {
		BbsUser user = findById(userId);
		if (point != null) {
			user.setPoint(user.getPoint() + point);
		}
		if (prestige != null) {
			user.setPrestige(user.getPrestige() + prestige);
		}
		// operator=-1无须下面操作
		if (StringUtils.isNotBlank(mid) && operator != -1) {
			BbsMemberMagic magic;
			BbsCommonMagic comMagic = magicMng.findByIdentifier(mid);
			magic = user.getMemberMagic(mid);
			// operator==0出售道具，=1使用道具 =2丢弃道具 =3购买道具=4系统赠送道具
			if (operator == 0) {
				// 用户存在该道具---减少数量
				if (magic != null) {
					magic.setNum(magic.getNum() - num);
					// 减少包容量
					user.setMagicPacketSize(user.getMagicPacketSize() - num
							* comMagic.getWeight());
					// 增加系统包数量
					comMagic.setNum(num + comMagic.getNum());
					magicMng.update(comMagic);
				}
			} else if (operator == 1) {
				// 减少数量
				if (magic != null) {
					magic.setNum(magic.getNum() - num);
					// 减少包容量
					user.setMagicPacketSize(user.getMagicPacketSize() - num
							* comMagic.getWeight());
				}
			} else if (operator == 2) {
				// 减少数量
				if (magic != null) {
					magic.setNum(magic.getNum() - num);
					// 减少包容量
					user.setMagicPacketSize(user.getMagicPacketSize() - num
							* comMagic.getWeight());
				}
			} else if (operator == 3) {
				// 增加数量
				if (magic != null) {
					magic.setNum(magic.getNum() + num);
					// 增加包容量
					user.setMagicPacketSize(user.getMagicPacketSize() + num
							* comMagic.getWeight());
					// 减少系统包数量
					comMagic.setNum(comMagic.getNum() - num);
					magicMng.update(comMagic);
				} else {
					magic = new BbsMemberMagic();
					magic.setMagic(comMagic);
					magic.setNum(num);
					magic.setUser(user);
					memberMagicMng.save(magic);
					user.addToMemberMagics(magic);
				}
			} else if (operator == 4) {
				// 增加数量
				if (magic != null) {
					magic.setNum(magic.getNum() + num);
					// 增加包容量
					user.setMagicPacketSize(user.getMagicPacketSize() + num
							* comMagic.getWeight());
					magicMng.update(comMagic);
				} else {
					magic = new BbsMemberMagic();
					magic.setMagic(comMagic);
					magic.setNum(num);
					magic.setUser(user);
					memberMagicMng.save(magic);
					user.addToMemberMagics(magic);
				}
			}

		}
	}
	

	// private CmsSiteMng cmsSiteMng;
	private BbsUserGroupMng bbsUserGroupMng;
	private UnifiedUserMng unifiedUserMng;
	private BbsUserExtMng bbsUserExtMng;
	private BbsUserDao dao;
	@Autowired
	private BbsCommonMagicMng magicMng;
	@Autowired
	private BbsMemberMagicMng memberMagicMng;
	@Autowired
	private BbsUserOnlineMng bbsUserOnlineMng;
	@Autowired
	private CmsConfigMng cmsConfigMng;
	@Autowired
	private BbsUserActiveLevelMng bbsUserActiveLevelMng;

	// @Autowired
	// public void setCmsSiteMng(CmsSiteMng cmsSiteMng) {
	// this.cmsSiteMng = cmsSiteMng;
	// }

	@Autowired
	public void setUnifiedUserMng(UnifiedUserMng unifiedUserMng) {
		this.unifiedUserMng = unifiedUserMng;
	}

	@Autowired
	public void setBbsUserExtMng(BbsUserExtMng bbsUserExtMng) {
		this.bbsUserExtMng = bbsUserExtMng;
	}

	@Autowired
	public void setDao(BbsUserDao dao) {
		this.dao = dao;
	}

	@Autowired
	public void setBbsUserGroupMng(BbsUserGroupMng bbsUserGroupMng) {
		this.bbsUserGroupMng = bbsUserGroupMng;
	}
}