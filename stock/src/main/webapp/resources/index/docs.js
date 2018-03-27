if (!Ext.BLANK_IMAGE_URL) {
	Ext.BLANK_IMAGE_URL = '../resources/index/s.gif';
}
Docs = {};
var menu = new Array();

var mainPanel;
function menuClick(node, e) {
	e.stopEvent();
	if(node.hasChildNodes()) {
		node.toggle();
	}
	if(node.attributes.href) {
		var text = node.text;
		if(!node.attributes.desc && node.attributes.desc != 'null') {
			text = node.attributes.desc;
		}
		mainPanel.loadClass(node.attributes.href,text);
	}
}

function fillChild(root, ar) {
	var _v, v, node, j;
	if (ar.children) {
		_v = ar.children;
		for (j = 0; j < _v.length; j++) {
			node = new Ext.tree.TreeNode({
						text : _v[j].text,
						desc: _v[j].desc,
						cls : 'mc_resenddoc',
						allowDrag : false,
						expanded:true,
						leaf: _v[j].children,
						href : _v[j].href
					});
			root.appendChild(node);
			fillChild(node, _v[j]);
		}
	}
}
function getMenu(_root) {
	if (_root) {

	} else if (Docs && Docs.classData && Docs.classData.children) {
		v = Docs.classData.children;
		for (i = 0; i < v.length; i++) {
			var href = 'null';
			if (v[i].href && v[i].href != "") {
				href = v[i].href;
			}
			var panel = new Ext.tree.TreePanel({
						border : false,
						id : v[i].id,
						shim : false,
						title : v[i].text,
						split : true,
						width : 136,
						minSize : 136,
						maxSize : 180,
						height : 100,
						collapsible : true,
						useArrows:true,
						margins : '0 0 0 0',
						href : href,
						loader : new Ext.tree.TreeLoader({}),
						rootVisible : false,
						draggable : false,
						allowDrag : false,
						lines : false,
						listeners : {
							'click' : {
								fn : menuClick
							}
						},
						autoScroll : true,
						root : new Ext.tree.AsyncTreeNode({
									text : '',
									expanded : true
								})
					});

			var root = new Ext.tree.TreeNode({
						id : v[i].id,
						text : '',
						allowDrag : false,
						allowDrop : false
					});
			panel.setRootNode(root);
			menu.push(panel);
			fillChild(root, v[i]);
		}
		return menu;
	}
}

DocPanel = Ext.extend(Ext.Panel, {
	closable : true,
	autoScroll : true,
	initComponent : function() {
		var ps = this.cclass.split('.');
		this.title = ps[ps.length - 1];

		DocPanel.superclass.initComponent.call(this);
	},

	scrollToMember : function(member) {
		var el = Ext.fly(this.cclass + '-' + member);
		if (el) {
			var top = (el.getOffsetsTo(this.body)[1]) + this.body.dom.scrollTop;
			this.body.scrollTo('top', top - 25, {
						duration : .75,
						callback : this.hlMember.createDelegate(this, [member])
					});
		}
	},

	scrollToSection : function(id) {
		var el = Ext.getDom(id);
		if (el) {
			var top = (Ext.fly(el).getOffsetsTo(this.body)[1])
					+ this.body.dom.scrollTop;
			this.body.scrollTo('top', top - 25, {
						duration : .5,
						callback : function() {
							Ext.fly(el).next('h2').pause(.2).highlight(
									'#8DB2E3', {
										attr : 'color'
									});
						}
					});
		}
	},

	hlMember : function(member) {
		var el = Ext.fly(this.cclass + '-' + member);
		if (el) {
			el.up('tr').highlight('#cadaf9');
		}
	}
});

MainPanel = function() {

	this.searchStore = new Ext.data.Store({
				proxy : new Ext.data.ScriptTagProxy({
							url : ''
						}),
				reader : new Ext.data.JsonReader({
							root : 'data'
						}, ['cls', 'member', 'type', 'doc']),
				baseParams : {},
				listeners : {
					'beforeload' : function() {
						this.baseParams.qt = Ext.getCmp('search-type')
								.getValue();
					}
				}
			});

	MainPanel.superclass.constructor.call(this, {
				id : 'doc-body',
				region : 'center',
				margins : '1 5 5 0',
				resizeTabs : true,
				minTabWidth : 135,
				tabWidth : 165,
				// plugins : new Ext.ux.TabCloseMenu(),
				enableTabScroll : true,
				autoScroll : true,
				activeTab : 0,
				// wheelIncrement : appConstants.TABPAGE_MAX_SUM, // tab打开的最大个数
				items : {
					id : 'welcome-panel',
					title : '系统页面',
					autoLoad: {url: docs_index_page},
					iconCls : 'icon-cls',
					closable : false,
					autoScroll : true
				}
			});
};

Ext.extend(MainPanel, Ext.TabPanel, {
	initEvents : function() {
		MainPanel.superclass.initEvents.call(this);
		this.body.on('click', this.onClick, this);
	},

	onClick : function(e, target) {
		if (target = e.getTarget('a:not(.exi)', 3)) {
			var cls = Ext.fly(target).getAttributeNS('ext', 'cls');
			e.stopEvent();
			if (cls) {
				var member = Ext.fly(target).getAttributeNS('ext', 'member');
				this.loadClass(target.href, cls, member);
			} else if (target.className == 'inner-link') {
				this.getActiveTab().scrollToSection(target.href.split('#')[1]);
			} else {
				window.open(target.href);
			}
		} else if (target = e.getTarget('.micon', 2)) {
			e.stopEvent();
			var tr = Ext.fly(target.parentNode);
			if (tr.hasClass('expandable')) {
				tr.toggleClass('expanded');
			}
		}
	},
	closeTab : function(cls) {
		var id = 'docs-' + cls;
		var tab = this.getComponent(id);
		if (tab) {
			this.remove(tab);
		}

	},
	loadClass : function(href, cls, member) {
		var id = 'docs-' + cls;

		var search = "";
		if (-1 != href.indexOf("?")) {
			var parts = href.split("?");
			href = parts[0];
			if (parts[1].length) {
				search = '&' + parts[1];
			}
		}
		var url = 'main!frameTransfer.do?navigateUrl=' + href + search;
		if (Ext.TRANSFER_URL) {
			url = Ext.TRANSFER_URL + '?navigateUrl=' + href + search;
		}
		var autoLoad = {
			url : url
		};
		var tab = this.getComponent(id);
		if (tab) {
			this.setActiveTab(tab);
			tab.load(autoLoad);
			if (member) {
				tab.scrollToMember(member);
			}

		} else if (this.items.length > appConstants.TABPAGE_MAX_SUM) {
			hs.MessageHelper.info({
						msg : '最多只能打开' + appConstants.TABPAGE_MAX_SUM
								+ '个标签页,请关闭空闲页!'
					});
		} else {

			if (member) {
				autoLoad.callback = function() {
					Ext.getCmp(id).scrollToMember(member);
				}
			}
			var p = this.add(new DocPanel({
						id : id,
						cclass : cls,
						autoLoad : autoLoad,
						autoScroll : false,
						iconCls : Docs.icons[cls]
					}));
			this.setActiveTab(p);
		}
	},

	initSearch : function() {
		// Custom rendering Template for the View
		var resultTpl = new Ext.XTemplate(
				'<tpl for=".">',
				'<div class="search-item">',
				'<a class="member" ext:cls="{cls}" ext:member="{member}" href="output/{cls}.html">',
				'<img src="../resources/images/default/s.gif" class="item-icon icon-{type}"/>{member}',
				'</a> ',
				'<a class="cls" ext:cls="{cls}" href="output/{cls}.html">{cls}</a>',
				'<p>{doc}</p>', '</div></tpl>');

		var p = new Ext.DataView({
					applyTo : 'search',
					tpl : resultTpl,
					loadingText : 'Searching...',
					store : this.searchStore,
					itemSelector : 'div.search-item',
					emptyText : ''
				});
	},

	doSearch : function(e) {
		var k = e.getKey();
		if (!e.isSpecialKey()) {
			var text = e.target.value;
			if (!text) {
				this.searchStore.baseParams.q = '';
				this.searchStore.removeAll();
			} else {
				this.searchStore.baseParams.q = text;
				this.searchStore.reload();
			}
		}
	}
});

//顶端工具栏
var toolbar =  new Ext.Toolbar({
    enableOverflow: true,
    items: [{
        xtype:'splitbutton',
        text: '系统管理员',
        iconCls: 'add16'
    },'-',{
        xtype:'splitbutton',
        text: '2010年6月18日 星期四',
        iconCls: 'add16',
        menu: [{text: 'Cut menu'}]
    },{
        text: '晴转多云 32度',
        iconCls: 'add16'
    },{
        text: 'Paste',
        iconCls: 'add16',
        menu: [{text: 'Paste menu'}]
    },'->',{
        text: ' 退出 ',
        iconCls: 'add16'
    }]
});

Ext.onReady(function() {
			Ext.QuickTips.init();
			mainPanel = new MainPanel();
			var sys_menu = getMenu();
			var hd = new Ext.Panel({
						border : false,
						layout : 'anchor',
						region : 'north',
						cls : 'docs-header',
						height : 46,
						//tbar: toolbar,
						items : [{
									xtype : 'box',
									el : 'header',
									border : false
								}]
					});

			var clock = new Ext.Toolbar.TextItem('');
			var viewport = new Ext.Viewport({
						layout : 'border',

						margins : '0 0 0 0',
						items : [hd, {
									id : 'west-panel',
									region : 'west',
									title : '功能菜单',
									split : true,
									border : true,
									width : 200,
									minSize : 100,
									maxSize : 240,
									collapsible : true,
									//collapseMode : 'mini',
									margins : '1 0 6 5',
									layout : {
										type : 'accordion',
										animate : true
									},
									items : sys_menu
								}, mainPanel]
					});
			viewport.doLayout();
			setTimeout(function() {
						Ext.get('loading').remove();
						Ext.get('loading-mask').fadeOut({
									remove : true
								});
					}, 250);
		});
