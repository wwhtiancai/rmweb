function OpeTable(tabObj) {
	this.table;
	this.ths = new Array();
	var owner = this;
	var _x;
	var _th;
	if (tabObj != null) {
		if (tabObj.nodeName == "TABLE") {
			this.table = tabObj;
			this._findThs();
			this.countTableWidth();
			for ( var i = 0; i < this.ths.length; i++) {
				var resize = document.createElement("DIV");
				resize.style.width = 10;
				resize.style.height = 16;
				resize.style.backgroundColor = "#06f";
				if (!IE) {
					resize.style.marginRight = "none";
					resize.style.marginLeft = "auto";
				}
				resize.onmousedown = _md;
				resize.style.cursor = "col-resize";
				this.ths[i].style.textAlign = "right";
				this.ths[i].appendChild(resize);
			}
		} else
			alert("Error : Parameter isn't a TABLE!");
	}
	function _md(event) {
		var e = window.event ? window.event : event;
		_x = IE ? e.x : e.pageX;
		_th = this.parentNode;
		BodyEventsManager.push("onmousemove", _mm);
		BodyEventsManager.push("onmouseup", _mu);
	}
	function _mu() {
		_x = null;
		_th = null;
		BodyEventsManager.pop("onmousemove");
		BodyEventsManager.pop("onmouseup");
	}
	function _mm(event) {
		var e = window.event ? window.event : event;
		var x = IE ? e.x : e.pageX;
		if (_x != null && _th != null) {
			var l = x - _x;
			_x = x;
			var w = parseInt(_th.width) + l;
			if (w > 20) {
				_th.width = w;
				owner.table.width = parseInt(owner.table.width) + l;
			}
		}
	}
}
OpeTable.prototype._findThs = function() {
	for ( var i = 0; i < this.table.childNodes.length; i++) {
		if (this.table.childNodes[i].nodeName == "TBODY") {
			var tbody = this.table.childNodes[i];
			for ( var j = 0; j < tbody.childNodes.length; j++) {
				if (tbody.childNodes[j].nodeName == "TR") {
					var tr = tbody.childNodes[j];
					for ( var k = 0; k < tr.childNodes.length; k++) {
						if (tr.childNodes[k].nodeName == "TH") {
							this.ths.push(tr.childNodes[k]);
						}
					}
					break;
				}
			}
		}
	}
};
OpeTable.prototype.countTableWidth = function() {
	var w = 0;
	for ( var i = 0; i < this.ths.length; i++) {
		if (this.ths[i].width == null || this.ths[i].width == "") {
			this.ths[i].width = 100;
			w += 100;
		} else {
			w += parseInt(this.ths[i].width);
		}
	}
	this.table.width = w;
};

