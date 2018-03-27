package cn.hzstk.securities.tencent.action;

import cn.hzstk.securities.common.action.BaseMagicAction;
import cn.hzstk.securities.stockeast.domain.BasicInfo;
import cn.hzstk.securities.stockeast.service.BasicInfoService;
import cn.hzstk.securities.stockeast.service.SelfSelectService;
import cn.hzstk.securities.sys.service.DictService;
import cn.hzstk.securities.task.service.CashRangeService;
import net.ryian.commons.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tencent")
public class TencentAction extends BaseMagicAction {

	@Autowired
	private SelfSelectService sService;

    @RequestMapping("tencentlist")
    public String tencentlist(HttpServletRequest request) {
        Map<String,String> params = getParameterMap(request);
        String dt = params.get("dt");
        if (StringUtils.isEmpty(dt)) dt = StringUtils.EMPTY;
        List<String> infos = sService.selectStockCode(dt);
        request.setAttribute("myStkList",infos);

        return super.getNameSpace()+"list/tencentlist";
    }

}
