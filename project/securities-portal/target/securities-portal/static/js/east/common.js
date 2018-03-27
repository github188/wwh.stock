; (function ($) {

})();

var yjfp_tools = {
    //日期格式化
    format_date: function (date_str) {
        if (date_str) {
            var year = date_str.substring(0, 4);
            var mon = date_str.substring(4, 6);
            var day = date_str.substring(6, 8);
        }
        return year + "-" + mon + "-" + day;
    },
    dateFormat: function (dateS, part) {
        if (dateS == "-" || typeof dateS == "undefined") {
            return '-';
        }
        if (dateS.length > 10) {
            dateS = dateS.split('T')[0].replace(/-/g, "/");
        }
        var date = new Date(dateS);
        var datecopy;
        var redate = "";
        part = (part == null) ? "yyyy-MM-dd HH:mm:ss" : part;
        var y = date.getFullYear();
        var M = date.getMonth() + 1;
        var d = date.getDate();
        var H = date.getHours();
        var m = date.getMinutes();
        var s = date.getSeconds();
        var MM = (M > 9) ? M : "0" + M;
        var dd = (d > 9) ? d : "0" + d;
        var HH = (H > 9) ? H : "0" + H;
        var mm = (m > 9) ? m : "0" + m;
        var ss = (s > 9) ? s : "0" + s;
        redate = part.replace("yyyy", y).replace("MM", MM).replace("dd", dd).replace("HH", HH).replace("mm", mm).replace("ss", ss).replace("M", M).replace("d", d).replace("H", H).replace("m", m).replace("s", s);
        return redate;
    },
    getNumData: function (number,tod) {
        try {
            if (number == "-" || typeof number == "undefined") {
                return '-';
            }
            return '<span class="sp_td">' + (number * 1).toFixed(tod) + '</span>';
        } catch (e) {
            return '-';
        }
    },
    getNumToYi: function (number) {
        try {
            if (number == "-" || typeof number == "undefined") {
                return '-';
            }
            return (number / 100000000).toFixed(2);
        } catch (e) {
            return '-';
        }
    },
    getNumToGuBen: function (number) {
        try {
            if (number == "-" || typeof number == "undefined") {
                return '-';
            }
            var v = (number / 100000000);
            if (v > 100)
                v = v.toFixed(0);
            else
                v = v.toFixed(2);
            return v;
        } catch (e) {
            return '-';
        }
    },
    getSZ: function (value,type)
    {
        var sr = '-';
        try {
            if (value == "-" || typeof value == "undefined") {
                return '-';
            }
            if (typeof type == "undefined")
                type = '送';
            var v = (value * 1).toFixed(1);
            if (v > 0)
                sr = '10' + type + v;
            else
                sr = '-';
            return sr;
        } catch (e) {
            return sr;
        }
    },
    getSZZB: function (val_S, val_Z) {
        var str = '-';
        try {
            if (val_S != "-" && typeof val_S != "undefined") {
                var vs = (val_S * 1).toFixed(1);
                if (vs > 0)
                    str = '10送' + vs;
                //else
                    //str = '10送0.0';
            }
            if (val_Z != "-" && typeof val_Z != "undefined") {
                var vz = (val_Z * 1).toFixed(1);
                if (vz > 0)
                    str = str == '-' ? '10转' + vz : str + '转' + vz;
                //else
                    //str = str == '-' ? '10转0.0' : str + '转0.0';
            }
            return str;

        } catch (e) {
            return str;
        }
    },
    getGXL: function (value)
    {
        try {
            if (value == "-" || typeof value == "undefined") {
                return '-';
            }
            return (value * 100).toFixed(2);
        } catch (e) {
            return '-';
        }
    },
    getFhbl: function (fh, gxl)
    {
        var res = '-';
        try {
            if (fh == "-" || typeof fh == "undefined") {
                return '-';
            }
            var v = (fh * 1).toFixed(4);
            res = '10派' + v;
        } catch (e) {
            return '-';
        }
        return res;
    },
    getColor: function (value) {
        var colorClass = "";
        try {
            var data = value * 1;
            if (data == "-" || typeof data == "undefined")
                return colorClass;
            if (data > 0)
                colorClass = "red";
            else if (data < 0)
                colorClass = "green";

        } catch (e) {

        }
        return colorClass;
    },
    getDataToNum: function (value) {
        try {
            var number = value;
            if (number == "-" || typeof number == "undefined") {
                return '-';
            }
            return (number * 1).toFixed(2);
        } catch (e) {
            return '-';
        }
    }
};