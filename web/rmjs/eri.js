ERI_NS = {

    PROVINCE : {},
    LICENCE_TYPE : {},
    VEHICLE_TYPE: {},
    VEHICLE_COLOR: {},
    USING_PURPOSE: {},

    /**
     *
     * @param number
     * @param length
     * @param mode
     */
    pad : function(number, length, mode) {
        var result = number + "";
        if (result.length < length) {
            for (var i = 0; i < length - (number + "").length; i++) {
                if (mode && mode == 1) {
                    result = result + "0";
                } else {
                    result = "0" + result;
                }
            }
        } else {
            result = result.substring(result.length - length);
        }
        return result;
    },

    parseLicensePlate : function(value) {
        return String.fromCharCode(value + 'a'.charCodeAt(0)).toUpperCase();
    },

    parseHphm : function(value) {
        if (value < 0 || value > Math.pow(36, 6) + Math.pow(36, 5) + Math.pow(36, 4))
            alert("∫≈≈∆∫≈¬Î ˝æ›¥ÌŒÛ");
        var hphm = "";
        var remainder = Number();
        var result = value;
        var hphmLength;
        if (value < Math.pow(36, 4)) {
            hphmLength = 4;
        } else if (value < Math.pow(36, 5) + Math.pow(36,4)) {
            hphmLength = 5;
            result = result - Math.pow(36, 4);
        } else {
            hphmLength = 6;
            result = result - Math.pow(36,4) - Math.pow(36, 5);
        }
        do {
            remainder = parseInt(result % 36);
            result = parseInt(result / 36);
            if (remainder < 26) {
                hphm = String.fromCharCode(65 + remainder) + hphm;
            } else {
                hphm = String.fromCharCode(48 + remainder - 26) + hphm;
            }
        } while ( result > 0);
        var l = hphm.length;
        for (var i = 0; i < hphmLength - l; i++) {
            hphm = "A" + hphm;
        }
        return hphm;
    },

    /**
     * @param data
     * @returns {*}
     */
    parse : function(data) {
        var result = {zt: 1};
        var cardType = parseInt(data.substring(0, 1), 16) >> 3;
        result["cardType"] = cardType;
        var sf = parseInt(data.substring(0, 2), 16) >> 1 & 0x3F;
        var kh = Number("0x" + data.substring(0, 10)) >> 3 & 0x3FFFFFFF;
        if (ERI_NS.PROVINCE[sf]) {
            result["kh"] = ERI_NS.PROVINCE[sf]["dmz"] + ERI_NS.pad(kh, 10);
        }
        sf = parseInt(data.substring(9, 11), 16) >> 1 & 0x3F;
        result["sf"] = ERI_NS.PROVINCE[sf];
        var fpdh = parseInt(data.substring(10, 12), 16) & 0x1F;
        result["fpdh"] = ERI_NS.parseLicensePlate(fpdh);
        var syxz = parseInt(data.substring(12, 13), 16);
        if (syxz == 00) {
            var hphm = Number("0x" + data.substring(21, 29));
            result["hphm"] = "√Ò∫ΩD" + ERI_NS.parseHphm(hphm);
            result["zt"] = 0;
            return result;
        }
        result["syxz"] = ERI_NS.USING_PURPOSE[syxz];
        var ccrq = parseInt(data.substring(13, 16), 16) >> 3;
        var now = new Date();
        var basic = new Date();
        basic.setFullYear(1990);
        basic.setMonth(0);
        basic.setDate(1);
        if ((now.getFullYear() - 1990) * 12 + now.getMonth() + 1 < ccrq ) {
            ccrq = ccrq - 511;
        }
        if (ccrq != 511) {
            basic.setFullYear(1990 + ccrq / 12);
            basic.setMonth(ccrq % 12);
            result["ccrq"] = basic.getFullYear() + "-" + (basic.getMonth() + 1);
        }
        var cllx = parseInt(data.substring(15, 18), 16) >> 2 & 0x01FF;
        result["cllx"] = ERI_NS.VEHICLE_TYPE[cllx];
        var glOrPlSign = parseInt(data.substring(17, 18), 16) >> 1 & 0x01;
        result["glOrPlSign"] = glOrPlSign;
        var hpzl = parseInt(data.substring(20, 21), 16);
        result["hpzl"] = ERI_NS.LICENCE_TYPE[hpzl];
        var hphm = Number("0x" + data.substring(21, 29));
        if (ERI_NS.PROVINCE[sf]) {
            result["hphm"] = ERI_NS.PROVINCE[sf]["dmsm2"] + result["fpdh"] + ERI_NS.parseHphm(hphm);
        }
        var yxqz = parseInt(data.substring(29, 32), 16) >> 3;
        basic.setFullYear(2013);
        basic.setMonth(0);
        basic.setDate(1);
        if (yxqz != 511) {
            basic.setFullYear(2013 + yxqz / 12);
            basic.setMonth(yxqz % 12);
            result["yxqz"] = basic.getFullYear() + "-" + (basic.getMonth() + 1);
        }
        var qzbfqz = parseInt(data.substring(31, 33), 16) >> 2 & 0x1F;
        if (yxqz != 511) {
            basic.setFullYear(basic.getFullYear() + qzbfqz);
            result["qzbfqz"] = basic.getFullYear();
        }
        var hdzkOrZzl = parseInt(data.substring(32, 35), 16) & 0x03FF;
        if (result["cllx"] && result["cllx"]["dmz"].substring(0,1).toUpperCase() == "K") {
            result["hdzk"] = hdzkOrZzl + "»À";
        } else {
            result["zzl"] = hdzkOrZzl / 10.0 + "∂÷";
        }
        var csys = parseInt(data.substring(35, 36), 16);
        result["csys"] = ERI_NS.VEHICLE_COLOR[csys];
        var plOrGl = parseInt(data.substring(36, 38), 16);
        if (glOrPlSign == 0) {
            result["pl"] = plOrGl / 10.0 + "L";
        } else {
            result["gl"] = plOrGl;
        }
        return result;
    },

    JsonToString: function(o) {
        var arr = [];
        var fmt = function(s) {
            if (typeof s == 'object' && s != null) return JsonToStr(s);
            return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s;
        };
        for (var i in o)
            arr.push("'" + i + "':" + fmt(o[i]));
        return '{' + arr.join(',') + '}';
    }

};