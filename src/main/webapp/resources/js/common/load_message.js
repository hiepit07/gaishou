(function($) {
	$.alerts = {
		// These properties can be read/written by accessing $.alerts.propertyName from your scripts at any time
		verticalOffset: -75,                // vertical offset of the dialog from center screen, in pixels
		horizontalOffset: 0,                // horizontal offset of the dialog from center screen, in pixels/
		repositionOnResize: true,           // re-centers the dialog on window resize
		overlayOpacity: .1,                // transparency level of overlay
		overlayColor: '#000000',               // base color of overlay
		dialogClass: null,                  // if specified, this class will be applied to all dialogs

		// Public methods
		info: function(message, title, okButton, cancelButton, callback) {
			if (title == null) title = 'Alert';
			$.alerts._show(title, message, okButton, cancelButton, 'info', function(result) {
				if (callback) callback(result);
			});
		},

		warning: function(message, title, okButton, cancelButton, callback) {
			if (title == null) title = 'Alert';
			$.alerts._show(title, message, okButton, cancelButton, 'warning', function(result) {
				if (callback) callback(result);
			});
		},

		info_confirm: function(message, title, okButton, cancelButton, callback) {
			if (title == null) title = 'Confirm';
			$.alerts._show(title, message, okButton, cancelButton, 'info_confirm', function(result) {
				if (callback) callback(result);
			});
		},

		question_confirm: function(message, title, okButton, cancelButton, callback) {
			if (title == null) title = 'Confirm';
			$.alerts._show(title, message, okButton, cancelButton, 'question_confirm', function(result) {
				if(callback) callback(result);
			});
		},

		confirm: function(message, title, okButton, cancelButton, callback) {
			if (title == null) title = 'Confirm';
			$.alerts._show(title, message, okButton, cancelButton, 'confirm', function(result) {
				if (callback) callback(result);
			});
		},

		error: function(message, title, okButton, cancelButton, callback) {
			if (title == null) title = 'Confirm';
			$.alerts._show(title, message, okButton, cancelButton, 'error', function(result) {
				if (callback) callback(result);
			});
		},

		message: function(message, title, okButton, cancelButton, callback) {
			if (title == null) title = 'Alert';
			$.alerts._show(title, message, okButton, cancelButton, 'message', function(result) {
				if (callback) callback(result);
			});
		},

		question_warning: function(message, title, okButton, cancelButton, callback) {
			if (title == null) title = 'Confirm';
			$.alerts._show(title, message, okButton, cancelButton, 'question_warning', function(result) {
				if (callback) callback(result);
			});
		},

		// Private methods
		_show: function(title, msg, okButton, cancelButton, type, callback) {

			$.alerts._hide();
			$.alerts._overlay('show');

			$("BODY").append(
			  '<div id="popup_container">' +
			    '<h1 id="popup_title"></h1>' +
			    '<div id="popup_content">' +
			      '<div id="popup_message"></div>' +
				'</div>' +
			  '</div>');

			if ($.alerts.dialogClass) $("#popup_container").addClass($.alerts.dialogClass);

			$("#popup_container").css({
				position: 'fixed',
				zIndex: 99999,
				padding: 0,
				margin: 0
			});

			$("#popup_title").text(title);
			$("#popup_content").addClass(type);
			$("#popup_message").text(msg);
			$("#popup_message").html($("#popup_message").text().replace(/\n/g, '<br />'));

			$("#popup_container").css({
				minWidth: $("#popup_container").outerWidth(),
				maxWidth: $("#popup_container").outerWidth()
			});

			$.alerts._reposition();
			$.alerts._maintainPosition(true);

			switch (type) {
				case 'info':
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="'
							+ okButton + '" id="popup_ok" class="cursor-pointer" /></div>');
					$("#popup_ok").click(function() {
						$.alerts._hide();
						callback(true);
					});
					$("#popup_ok").focus().keypress(function(e) {
						if (e.keyCode == 13 || e.keyCode == 27) $("#popup_ok").trigger('click');
					});
				break;
				case 'warning':
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="'
							+ okButton + '" id="popup_ok" class="cursor-pointer" /></div>');
					$("#popup_ok").click(function() {
						$.alerts._hide();
						callback(true);
					});
					$("#popup_ok").focus().keypress(function(e) {
						if (e.keyCode == 13 || e.keyCode == 27) $("#popup_ok").trigger('click');
					});
				break;
				case 'info_confirm':
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="'
							+ okButton + '" id="popup_ok" class="cursor-pointer" /> <input type="button" value="'
							+ cancelButton + '" id="popup_cancel" class="cursor-pointer" /></div>');
					$("#popup_ok").click(function() {
						$.alerts._hide();
						if (callback) callback(true);
					});
					$("#popup_cancel").click(function() {
						$.alerts._hide();
						if (callback) callback(false);
					});
					$("#popup_ok").focus();
					$("#popup_ok, #popup_cancel").keypress(function(e) {
						if (e.keyCode == 13) $("#popup_ok").trigger('click');
						if (e.keyCode == 27) $("#popup_cancel").trigger('click');
					});
				break;
				case 'question_confirm':
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="'
							+ okButton + '" id="popup_ok" class="cursor-pointer" /> <input type="button" value="'
							+ cancelButton + '" id="popup_cancel" class="cursor-pointer" /></div>');
					$("#popup_ok").click(function() {
						$.alerts._hide();
						if (callback) callback(true);
					});
					$("#popup_cancel").click(function() {
						$.alerts._hide();
						if (callback) callback(false);
					});
					// focus on cancel button
					//$("#popup_ok").focus();
					$("#popup_cancel").focus();
					$("#popup_ok, #popup_cancel").keypress(function(e) {
						if (e.keyCode == 13) $("#popup_ok").trigger('click');
						if (e.keyCode == 27) $("#popup_cancel").trigger('click');
					});
				break;
				case 'confirm':
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="'
							+ okButton + '" id="popup_ok" class="cursor-pointer" /> <input type="button" value="'
							+ cancelButton + '" id="popup_cancel" class="cursor-pointer" /></div>');
					$("#popup_ok").click(function() {
						$.alerts._hide();
						if (callback) callback(true);
					});
					$("#popup_cancel").click(function() {
						$.alerts._hide();
						if (callback) callback(false);
					});
					$("#popup_ok").focus();
					$("#popup_ok, #popup_cancel").keypress(function(e) {
						if (e.keyCode == 13) $("#popup_ok").trigger('click');
						if (e.keyCode == 27) $("#popup_cancel").trigger('click');
					});
				break;
				case 'error':
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="'
							+ okButton + '" id="popup_ok" class="cursor-pointer" /></div>');
					$("#popup_ok").click(function() {
						$.alerts._hide();
						callback(true);
					});
					$("#popup_ok").focus().keypress(function(e) {
						if (e.keyCode == 13 || e.keyCode == 27) $("#popup_ok").trigger('click');
					});
				break;
				case 'message':
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="'
							+ okButton + '" id="popup_ok" class="cursor-pointer" /></div>');
					$("#popup_ok").click(function() {
						$.alerts._hide();
						callback(true);
					});
					$("#popup_ok").focus().keypress(function(e) {
						if (e.keyCode == 13 || e.keyCode == 27) $("#popup_ok").trigger('click');
					});
				break;
				case 'question_warning':
					$("#popup_message").after('<div id="popup_panel"><input type="button" value="'
							+ okButton + '" id="popup_ok" class="cursor-pointer" /> <input type="button" value="'
							+ cancelButton + '" id="popup_cancel" class="cursor-pointer" /></div>');
					$("#popup_ok").click(function() {
						$.alerts._hide();
						if (callback) callback(true);
					});
					$("#popup_cancel").click(function() {
						$.alerts._hide();
						if (callback) callback(false);
					});
					$("#popup_cancel").focus();
			}

			$("#popup_ok, #popup_cancel").keydown(function(e) {
				if (e.keyCode == 8) e.preventDefault();
			});
		},

		_hide: function() {
			$("#popup_container").remove();
			$.alerts._overlay('hide');
			$.alerts._maintainPosition(false);
		},

		_overlay: function(status) {
			switch (status) {
				case 'show':
					$.alerts._overlay('hide');
					$("BODY").append('<div id="popup_overlay"></div>');
					$("#popup_overlay").css({
						position: 'absolute',
						border: '0px',
						padding: '0px',
						margin: '0px',
						zIndex: 99998,
						top: '0px',
						left: '0px',
						bottom: '0px',
						width: '100%',
						height: $(document).height() - 5,
						background: $.alerts.overlayColor,
						opacity: $.alerts.overlayOpacity
					});
				break;
				case 'hide':
					$("#popup_overlay").remove();					
				break;
			}
		},

		_reposition: function() {
			var top = (($(window).height() / 2) - ($("#popup_container").outerHeight() / 2) + 20) + $.alerts.verticalOffset;
			var left = (($(window).width() / 2) - ($("#popup_container").outerWidth() / 2)) + $.alerts.horizontalOffset;

			if (top < 0) top = 0;
			if (left < 0) left = 0;

			$("#popup_container").css({
				top: top + 'px',
				left: left + 'px'
			});
			$("#popup_overlay").height( $(document).height() - 5);
		},

		_maintainPosition: function(status) {
			if ($.alerts.repositionOnResize) {
				switch (status) {
					case true:
						$(window).bind('resize', $.alerts._reposition);
					break;
					case false:
						$(window).unbind('resize', $.alerts._reposition);
					break;
				}
			}
		}
	}

	// Shortcut functions
	jInfo = function(message, title, okButton, cancelButton, callback) {
		$.alerts.info(message, title, okButton, cancelButton, callback);
	}

	jWarning = function(message, title, okButton, cancelButton, callback) {
		$.alerts.warning(message, title, okButton, cancelButton, callback);
	}

	jInfo_confirm = function(message, title, okButton, cancelButton, callback) {
		$.alerts.info_confirm(message, title, okButton, cancelButton, callback);
	};

	jQuestion_confirm = function(message, title, okButton, cancelButton, callback) {
		$.alerts.question_confirm(message, title, okButton, cancelButton, callback);
	};

	jConfirm = function(message, title, okButton, cancelButton, callback) {
		$.alerts.confirm(message, title, okButton, cancelButton, callback);
	};

	jError = function(message, title, okButton, cancelButton, callback) {
		$.alerts.error(message, title, okButton, cancelButton, callback);
	};

	jMessage = function(message, title, okButton, cancelButton, callback) {
		$.alerts.message(message, title, okButton, cancelButton, callback);
	};

	jQuestion_warning = function(message, title, okButton, cancelButton, callback) {
		$.alerts.question_warning(message, title, okButton, cancelButton, callback);
	};

})(jQuery);