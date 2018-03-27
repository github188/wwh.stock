Ext.BLANK_IMAGE_URL = '../resources/index/s.gif';

Docs = {};

ApiPanel = function() {
    ApiPanel.superclass.constructor.call(this, {
        id:'api-tree',
        region:'west',
        split:true,
        width: 200,
        minSize: 175,
        maxSize: 450,
        collapsible: true,
        margins:'0 0 5 5',
        cmargins:'0 0 0 0',
        rootVisible:false,
        lines:false,
        autoScroll:true,
        animCollapse:false,
        animate: false,
        collapseMode:'mini',
        loader: new Ext.tree.TreeLoader({
			preloadChildren: true,
			clearOnLoad: false
		}),
        root: new Ext.tree.AsyncTreeNode({
            text:'Ext JS',
            id:'root',
            expanded:true,
            children:[Docs.classData]
         }),
        collapseFirst:false
    });
    // no longer needed!
    //new Ext.tree.TreeSorter(this, {folderSort:true,leafAttr:'isClass'});

    this.getSelectionModel().on('beforeselect', function(sm, node){
        return node.isLeaf();
    });
};

Ext.extend(ApiPanel, Ext.tree.TreePanel, {
    initComponent: function(){
        this.hiddenPkgs = [];
        Ext.apply(this, {
            tbar:[ ' ',
			new Ext.form.TextField({
				width: 200,
				emptyText:'Find a Class',
                enableKeyEvents: true,
				listeners:{
					render: function(f){
                    	this.filter = new Ext.tree.TreeFilter(this, {
                    		clearBlank: true,
                    		autoClear: true
                    	});
					},
                    keydown: {
                        fn: this.filterTree,
                        buffer: 350,
                        scope: this
                    },
                    scope: this
				}
			}), ' ', ' ',
			{
                iconCls: 'icon-expand-all',
				tooltip: 'Expand All',
                handler: function(){ this.root.expand(true); },
                scope: this
            }, '-', {
                iconCls: 'icon-collapse-all',
                tooltip: 'Collapse All',
                handler: function(){ this.root.collapse(true); },
                scope: this
            }]
        })
        ApiPanel.superclass.initComponent.call(this);
    },
	filterTree: function(t, e){
		var text = t.getValue();
		Ext.each(this.hiddenPkgs, function(n){
			n.ui.show();
		});
		if(!text){
			this.filter.clear();
			return;
		}
		this.expandAll();
		
		var re = new RegExp('^' + Ext.escapeRe(text), 'i');
		this.filter.filterBy(function(n){
			return !n.attributes.isClass || re.test(n.text);
		});
		
		// hide empty packages that weren't filtered
		this.hiddenPkgs = [];
                var me = this;
		this.root.cascade(function(n){
			if(!n.attributes.isClass && n.ui.ctNode.offsetHeight < 3){
				n.ui.hide();
				me.hiddenPkgs.push(n);
			}
		});
	},
    selectClass : function(cls){
        if(cls){
            var parts = cls.split('.');
            var last = parts.length-1;
            for(var i = 0; i < last; i++){ // things get nasty - static classes can have .
                var p = parts[i];
                var fc = p.charAt(0);
                var staticCls = fc.toUpperCase() == fc;
                if(p == 'Ext' || !staticCls){
                    parts[i] = 'pkg-'+p;
                }else if(staticCls){
                    --last;
                    parts.splice(i, 1);
                }
            }
            parts[last] = cls;

            this.selectPath('/root/apidocs/'+parts.join('/'));
        }
    }
});


DocPanel = Ext.extend(Ext.Panel, {
    closable: true,
    autoScroll:true,
    initComponent : function(){
        var ps = this.cclass.split('.');
        this.title = ps[ps.length-1];
		
        DocPanel.superclass.initComponent.call(this);
    },

    scrollToMember : function(member){
        var el = Ext.fly(this.cclass + '-' + member);
        if(el){
            var top = (el.getOffsetsTo(this.body)[1]) + this.body.dom.scrollTop;
            this.body.scrollTo('top', top-25, {duration:.75, callback: this.hlMember.createDelegate(this, [member])});
        }
    },

	scrollToSection : function(id){
		var el = Ext.getDom(id);
		if(el){
			var top = (Ext.fly(el).getOffsetsTo(this.body)[1]) + this.body.dom.scrollTop;
			this.body.scrollTo('top', top-25, {duration:.5, callback: function(){
                Ext.fly(el).next('h2').pause(.2).highlight('#8DB2E3', {attr:'color'});
            }});
        }
	},

    hlMember : function(member){
        var el = Ext.fly(this.cclass + '-' + member);
        if(el){
            el.up('tr').highlight('#cadaf9');
        }
    }
});


MainPanel = function(){
	
	this.searchStore = new Ext.data.Store({
        proxy: new Ext.data.ScriptTagProxy({
            url: ''
        }),
        reader: new Ext.data.JsonReader({
	            root: 'data'
	        }, 
			['cls', 'member', 'type', 'doc']
		),
		baseParams: {},
        listeners: {
            'beforeload' : function(){
                this.baseParams.qt = Ext.getCmp('search-type').getValue();
            }
        }
    }); 
	
    MainPanel.superclass.constructor.call(this, {
        id:'doc-body',
        region:'center',
        margins:'0 5 5 0',
        resizeTabs: true,
        minTabWidth: 135,
        tabWidth: 165,
        plugins: new Ext.ux.TabCloseMenu(),
        enableTabScroll: true,
        autoScroll: true,
        activeTab: 0,
        wheelIncrement: appConstants.TABPAGE_MAX_SUM,		//tab打开的最大个数
        items: {
            id:'welcome-panel',
            title: '系统页面',
            //autoLoad: {url: 'welcome.html', callback: this.initSearch, scope: this},
            iconCls:'icon-cls',
            closable: false,
            autoScroll: true
        }
    });
};

Ext.extend(MainPanel, Ext.TabPanel, {
    initEvents : function(){
        MainPanel.superclass.initEvents.call(this);
        this.body.on('click', this.onClick, this);
    },

    onClick: function(e, target){
        if(target = e.getTarget('a:not(.exi)', 3)){
            var cls = Ext.fly(target).getAttributeNS('ext', 'cls');
            e.stopEvent();
            if(cls){
                var member = Ext.fly(target).getAttributeNS('ext', 'member');
                this.loadClass(target.href, cls, member);
            }else if(target.className == 'inner-link'){
                this.getActiveTab().scrollToSection(target.href.split('#')[1]);
            }else{
                window.open(target.href);
            }
        }else if(target = e.getTarget('.micon', 2)){
            e.stopEvent();
            var tr = Ext.fly(target.parentNode);
            if(tr.hasClass('expandable')){
                tr.toggleClass('expanded');
            }
        }
    },

    loadClass : function(href, cls, member){
        var id = 'docs-' + cls;
        var tab = this.getComponent(id);
        if(tab){
            this.setActiveTab(tab);
            if(member){
                tab.scrollToMember(member);
            }
        }else if(this.items.length > appConstants.TABPAGE_MAX_SUM){
        	hs.MessageHelper.info({msg:'最多只能打开' + appConstants.TABPAGE_MAX_SUM + '个标签页,请关闭空闲页!'});
        }else{
            var autoLoad = {url: 'main!frameTransfer.do?navigateUrl=' + href};
            if(member){
                autoLoad.callback = function(){
                    Ext.getCmp(id).scrollToMember(member);
                }
            }
            var p = this.add(new DocPanel({
                id: id,
                cclass : cls,
                autoLoad: autoLoad,
                iconCls: Docs.icons[cls]
            }));
            this.setActiveTab(p);
        }
    },
	
	initSearch : function(){
		// Custom rendering Template for the View
	    var resultTpl = new Ext.XTemplate(
	        '<tpl for=".">',
	        '<div class="search-item">',
	            '<a class="member" ext:cls="{cls}" ext:member="{member}" href="output/{cls}.html">',
				'<img src="../resources/images/default/s.gif" class="item-icon icon-{type}"/>{member}',
				'</a> ',
				'<a class="cls" ext:cls="{cls}" href="output/{cls}.html">{cls}</a>',
	            '<p>{doc}</p>',
	        '</div></tpl>'
	    );
		
		var p = new Ext.DataView({
            applyTo: 'search',
			tpl: resultTpl,
			loadingText:'Searching...',
            store: this.searchStore,
            itemSelector: 'div.search-item',
			emptyText: ''
        });
	},
	
	doSearch : function(e){
		var k = e.getKey();
		if(!e.isSpecialKey()){
			var text = e.target.value;
			if(!text){
				this.searchStore.baseParams.q = '';
				this.searchStore.removeAll();
			}else{
				this.searchStore.baseParams.q = text;
				this.searchStore.reload();
			}
		}
	}
});


Ext.onReady(function(){
    Ext.QuickTips.init();
    var api = new ApiPanel();
    var mainPanel = new MainPanel();
    api.on('click', function(node, e){
         if(node.isLeaf()){
            e.stopEvent();
            mainPanel.loadClass(node.attributes.href, node.text);
         }
    });

    mainPanel.on('tabchange', function(tp, tab){
        api.selectClass(tab.cclass); 
    });

    var hd = new Ext.Panel({
        border: false,
        layout:'anchor',
        region:'north',
        cls: 'docs-header',
        height:37,
        items: [{
            xtype:'box',
            el:'header',
            border:false
        }
//       , new Ext.Toolbar({
//            cls:'top-toolbar',
//            items:[ ' ',
//			new Ext.form.TextField({
//				width: 205,
//				emptyText:'系统主菜单功能点搜索',
//				listeners:{
//					render: function(f){
//						f.el.on('keydown', filterTree, f, {buffer: 350});
//					}
//				}
//			}), ' ', ' ',
//			{
//                iconCls: 'icon-expand-all',
//				tooltip: '展开功能点',
//                handler: function(){ api.root.expand(true); }
//            }, '-', {
//                iconCls: 'icon-collapse-all',
//                tooltip: '收拢功能点',
//                handler: function(){ api.root.collapse(true); }
//            }
//            , '->', {
//                tooltip:'Hide Inherited Members',
//                iconCls: 'icon-hide-inherited',
//                enableToggle: true,
//                toggleHandler : function(b, pressed){
//                     mainPanel[pressed ? 'addClass' : 'removeClass']('hide-inherited');
//                }
//            }, '-', {
//                tooltip:'Expand All Members',
//                iconCls: 'icon-expand-members',
//                enableToggle: true,
//                toggleHandler : function(b, pressed){
//                    mainPanel[pressed ? 'addClass' : 'removeClass']('full-details');
//                }
//            }
//              ]
//        })
        ]
    });


   var clock = new Ext.Toolbar.TextItem('');
//   var bottom=new Ext.Panel({
//   	    title:'',
//        border: false,
//        layout:'anchor',
//        region:'south',
//        cls: 'docs-bottom',
//        height:5,
//        //bbar: new Ext.StatusBar({items: [clock, ' ']}),
//        items: {
//        listeners: {
//            'render': {
//                fn: function(){
//                    Ext.fly(clock.getEl().parentNode).addClass('x-status-text-panel');
//				    Ext.TaskMgr.start({
//				        run: function(){
//				            Ext.fly(clock.getEl()).update(new Date().format('g:i:s A'));
//				        },
//				        interval: 1000
//				    });
//                }
//            }
//        }
//    }
//  });
    var viewport = new Ext.Viewport({layout:'border',items:[ hd, api, mainPanel]});
    api.expandPath('/root/apidocs');
    api.getNodeById("0").expand();//展开TREE中ID为0的节点
//    api.expandAll();//展开TREE中所有节点
    viewport.doLayout();
	setTimeout(function(){
        Ext.get('loading').remove();
        Ext.get('loading-mask').fadeOut({remove:true});
    }, 250);
	
	var filter = new Ext.tree.TreeFilter(api, {
		clearBlank: true,
		autoClear: true
	});
	var hiddenPkgs = [];
	function filterTree(e){
		var text = e.target.value;
		Ext.each(hiddenPkgs, function(n){
			n.ui.show();
		});
		if(!text){
			filter.clear();
			return;
		}
		api.expandAll();
		
		var re = new RegExp('^' + Ext.escapeRe(text), 'i');
		filter.filterBy(function(n){
			return !n.attributes.isClass || re.test(n.text);
		});
		
		// hide empty packages that weren't filtered
		hiddenPkgs = [];
		api.root.cascade(function(n){
			if(!n.attributes.isClass && n.ui.ctNode.offsetHeight < 3){
				n.ui.hide();
				hiddenPkgs.push(n);
			}
		});
	}
	
});

