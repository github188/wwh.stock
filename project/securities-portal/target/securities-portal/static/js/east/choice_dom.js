(function ($) {
    var tabDom = {
        init: function () {
            tabDom.setDom();
        },
        set: {
            id: "tabs",
            url: "/stockeast/xuangudata",
            data: {}
        },
        loadjson: function () {
            var _this = this;
            $.ajax({
                url: _this.set.url,
                cache: false,
                async: false,
                dataType: "json",
                success: function (json) {
                    _this.set.data = json;
                }
            });
        },
        setDom: function () {
            var _this = this;
            _this.loadjson()
            _this.setDom_tabs();
            //_this.setDom_secend();
        },
        setDom_tabs: function () {
            var _this = this;
            var TabTitle = document.createElement("div");
            TabTitle.className = "TabTitle";
            var tabs1 = document.createElement("ul");
            tabs1.className = "tabs";
            tabs1.id = "tabs1";
            var li_html = [];
            li_html[0] = '<li class=' + _this.set.data.tabs[0].li[0].focus + ' style="margin-left: 0px;"><span class="clkSpan">' + _this.set.data.tabs[0].li[0].name + '</span></li>'
            for (var i = 1; i < _this.set.data.tabs[0].li.length; i++) {
                li_html[i] = '<li><span class="clkSpan">' + _this.set.data.tabs[0].li[i].name + '</span></li>';
            };
            li_html.join("");
            $(tabs1).append(li_html);
            TabTitle.appendChild(tabs1);
            document.getElementById(_this.set.id).appendChild(TabTitle);
            _this.setDom_cont()
        },
        setDom_cont: function () {
            var _this = this;
            var tabs1_cont = [];
            var group = [];
            for (var i = 0; i < _this.set.data.cont.length; i++) {
                tabs1_cont[i] = document.createElement("div");
                tabs1_cont[i].id = "tabs1_cont" + (i + 1);
                tabs1_cont[i].className = "list listdata";
                tabs1_cont[i].style.display = "none";
                group[i] = document.createElement("div");
                group[i].className = "group clearfix";
                var list = [];
                for (var j = 0; j < _this.set.data.cont[i].list_cont.length; j++) {
                    list[j] = document.createElement("div");
                    list[j].className = "list font12";
                    var pre_tit = [];
                    var pre_span = [];
                    var pre_ul = [];
                    var txt = [];
                    var items = [];
                    if (_this.set.data.cont[i].items == false) {
                        if (_this.set.data.cont[i].list_cont[j].pre_tit != false) {
                            pre_tit[j] = document.createElement("div");
                            pre_tit[j].className = "pre_tit";
                            pre_span[j] = document.createElement("span");
                            pre_span[j].id = _this.set.data.cont[i].list_cont[j].pre_tit;
                            txt[j] = document.createTextNode(_this.set.data.cont[i].list_cont[j].name + ":");
                            pre_ul[j] = document.createElement("ul");
                            pre_ul[j].className = "pre_ul";
                            var li_html = [];
                            if (_this.set.data.cont[i].list_cont[j].ul[0].ck.indexOf('showChildConditions') == -1)
                                li_html[0] = '<li class="on dispNone" sortable="true" id="' + _this.set.data.cont[i].list_cont[j].ul[0].id + '" single="false"><span class="clkSpan" onclick="' + _this.set.data.cont[i].list_cont[j].ul[0].ck + '">' + _this.set.data.cont[i].list_cont[j].ul[0].text + '</span></li>';
                            else
                                li_html[0] = '<li class="on dispNone" id="' + _this.set.data.cont[i].list_cont[j].ul[0].id + '" single="false"><span class="clkSpan" onmouseover="' + _this.set.data.cont[i].list_cont[j].ul[0].ck + '">' + _this.set.data.cont[i].list_cont[j].ul[0].text + '</span></li>';
                            for (var k = 1; k < _this.set.data.cont[i].list_cont[j].ul.length; k++) {
                                if (_this.set.data.cont[i].list_cont[j].ul[k].ck.indexOf('showChildConditions') == -1)
                                    li_html[k] = '<li class="has" sortable="true" id="' + _this.set.data.cont[i].list_cont[j].ul[k].id + '" unit="' + _this.set.data.cont[i].list_cont[j].ul[k].unit + '"><span class="clkSpan" onclick="' + _this.set.data.cont[i].list_cont[j].ul[k].ck + '">' + _this.set.data.cont[i].list_cont[j].ul[k].text + '</span></li>';
                                else
                                    li_html[k] = '<li class="has" id="' + _this.set.data.cont[i].list_cont[j].ul[k].id + '" unit="' + _this.set.data.cont[i].list_cont[j].ul[k].unit + '"><span class="clkSpan" onmouseover="' + _this.set.data.cont[i].list_cont[j].ul[k].ck + '">' + _this.set.data.cont[i].list_cont[j].ul[k].text + '</span></li>';
                            }
                            li_html.join("");
                            $(pre_ul[j]).append(li_html);
                            pre_span[j].appendChild(txt[j]);
                            pre_tit[j].appendChild(pre_span[j]);
                            list[j].appendChild(pre_tit[j]);
                            list[j].appendChild(pre_ul[j]);
                        }
                    } else {
                        items[j] = document.createElement("ul");
                        items[j].className = "items";
                        var li_html = [];

                        var clickStr = 'onclick';
                        var sortableStr = 'sortable=\"true\"';
                        if (_this.set.data.cont[i].list_cont[j].ul[0].ck.indexOf('showChildConditions') != -1) {
                            clickStr = 'onmouseover';
                            sortableStr = '';
                        }
                        if (_this.set.data.cont[i].list_cont[j].ul[0].id == "gf_def") {
                            li_html[0] = '<li class="on dispNone" ' + sortableStr + ' id="' + _this.set.data.cont[i].list_cont[j].ul[0].id + '" single="true"><span class="clkSpan" ' + clickStr + '="' + _this.set.data.cont[i].list_cont[j].ul[0].ck + '">' + _this.set.data.cont[i].list_cont[j].ul[0].text + '</span></li>';
                        } else if (_this.set.data.cont[i].list_cont[j].ul[0].text.indexOf("不限") != -1) {
                            li_html[0] = '<li class="on dispNone" ' + sortableStr + ' id="' + _this.set.data.cont[i].list_cont[j].ul[0].id + '" single="false"><span class="clkSpan" ' + clickStr + '="' + _this.set.data.cont[i].list_cont[j].ul[0].ck + '">' + _this.set.data.cont[i].list_cont[j].ul[0].text + '</span></li>';
                        } else {
                            li_html[0] = '<li class="has" ' + sortableStr + ' id="' + _this.set.data.cont[i].list_cont[j].ul[0].id + '" clf="' + _this.set.data.cont[i].list_cont[j].ul[0].clf + '" unit="' + _this.set.data.cont[i].list_cont[j].ul[0].unit + '"><span class="clkSpan" ' + clickStr + '="' + _this.set.data.cont[i].list_cont[j].ul[0].ck + '">' + _this.set.data.cont[i].list_cont[j].ul[0].text + '</span></li>';
                        }

                        for (var k = 1; k < _this.set.data.cont[i].list_cont[j].ul.length; k++) {
                            if (_this.set.data.cont[i].list_cont[j].ul[k].ck.indexOf('showChildConditions') == -1)
                                li_html[k] = '<li class="has" sortable="true" id="' + _this.set.data.cont[i].list_cont[j].ul[k].id + '" clf="' + _this.set.data.cont[i].list_cont[j].ul[k].clf + '"  unit="' + _this.set.data.cont[i].list_cont[j].ul[k].unit + '"><span class="clkSpan" onclick="' + _this.set.data.cont[i].list_cont[j].ul[k].ck + '">' + _this.set.data.cont[i].list_cont[j].ul[k].text + '</span></li>';
                            else
                                li_html[k] = '<li class="has" id="' + _this.set.data.cont[i].list_cont[j].ul[k].id + '" clf="' + _this.set.data.cont[i].list_cont[j].ul[k].clf + '"  unit="' + _this.set.data.cont[i].list_cont[j].ul[k].unit + '"><span class="clkSpan" onmouseover="' + _this.set.data.cont[i].list_cont[j].ul[k].ck + '">' + _this.set.data.cont[i].list_cont[j].ul[k].text + '</span></li>';
                        }

                        li_html.join("");
                        $(items[j]).append(li_html);
                        list[j].appendChild(items[j]);
                    };
                    if (_this.set.data.cont[i].list_cont[j].colsable != false) {
                        var selectorcache = [];
                        selectorcache[j] = document.createElement("div");
                        selectorcache[j].className = "selectorcache";
                        var ul_cache = [];
                        for (var n = 0; n < _this.set.data.cont[i].list_cont[j].selectorcache[0].ul.length; n++) {
                            ul_cache[n] = document.createElement("ul");
                            ul_cache[n].id = _this.set.data.cont[i].list_cont[j].selectorcache[0].ul[n].id;
                            var li_cache = [];
                            li_cache[0] = '<li class="on dispNone" id="' + _this.set.data.cont[i].list_cont[j].selectorcache[0].ul[n].li[0].id + '" single="true"><span class="clkSpan" onclick="' + _this.set.data.cont[i].list_cont[j].selectorcache[0].ul[n].li[0].ck + '">' + _this.set.data.cont[i].list_cont[j].selectorcache[0].ul[n].li[0].text + '</span></li>';
                            for (var m = 1; m < _this.set.data.cont[i].list_cont[j].selectorcache[0].ul[n].li.length; m++) {
                                li_cache[m] = '<li id="' + _this.set.data.cont[i].list_cont[j].selectorcache[0].ul[n].li[m].id + '" sortable="true" sorttype="-1"><span class="clkSpan" onclick="' + _this.set.data.cont[i].list_cont[j].selectorcache[0].ul[n].li[m].ck + '">' + _this.set.data.cont[i].list_cont[j].selectorcache[0].ul[n].li[m].text + '</span></li>';
                            }
                            li_cache.join("");
                            $(ul_cache[n]).append(li_cache);
                            selectorcache[j].appendChild(ul_cache[n]);
                        }
                        list[j].appendChild(selectorcache[j]);
                    }

                    group[i].appendChild(list[j]);
                }
                tabs1_cont[i].appendChild(group[i]);
                document.getElementById(_this.set.id).appendChild(tabs1_cont[i]);
            };
            tabs1_cont[0].style.display = "block";

        }
    };
    tabDom.init();
})(jQuery);
