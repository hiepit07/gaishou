/* Japanese initialisation for the jQuery UI date picker plugin. */
/* Written by Kentaro SATO (kentaro@ranvis.com). */
jQuery(function($){
	$.datepicker.regional['jp'] = {
		closeText: '閉じる',
		prevText: '&#x3c;前',
		nextText: '次&#x3e;',
		currentText: '今日',
		monthNames: ['1月','2月','3月','4月','5月','6月',
		'7月','8月','9月','10月','11月','12月'],
		monthNamesShort: ['1月','2月','3月','4月','5月','6月',
		'7月','8月','9月','10月','11月','12月'],
		dayNames: ['日曜日','月曜日','火曜日','水曜日','木曜日','金曜日','土曜日'],
		dayNamesShort: ['日','月','火','水','木','金','土'],
		dayNamesMin: ['日','月','火','水','木','金','土'],
		weekHeader: '週',
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		showMonthAfterYear: true,
		yearSuffix: '年'};
	
	$.datepicker.regional['vi'] = {
			closeText: 'Đóng',
			prevText: '&#x3c;Trước',
			nextText: 'Sau&#x3e;',
			currentText: 'Hôm nay',
			monthNames: ['Tháng 1','Tháng 2','Tháng 3','Tháng 4','Tháng 5','Tháng 6',
			'Tháng 7','Tháng 8','Tháng 9','Tháng 10','Tháng 11','Tháng 12'],
			monthNamesShort: ['Th1','Th2','Th3','Th4','Th5','Th6',
			'Th7','Th8','Th9','Th10','Th11','Th12'],
			dayNames: ['Chủ Nhật','Thứ 2','Thứ 3','Thứ 4','Thứ 5','Thứ 6','Thứ 7'],
			dayNamesShort: ['CN','Hai','Ba','Tư','Năm','Sáu','Bảy'],
			dayNamesMin: ['CN','T2','T3','T4','T5','T6','T7'],
			weekHeader: 'Tuần',
			dateFormat: 'yy/mm/dd',
			firstDay: 0,
			isRTL: false,
			showMonthAfterYear: true,
			yearSuffix: ''};
	
	$.datepicker.regional['en'] = {
			closeText: 'Close',
			prevText: '&#x3c;Prev',
			nextText: 'Next&#x3e;',
			currentText: 'Today',
			monthNames: ['January','February','March','April','May','June',
			'July','August','September','October','November','December'],
			monthNamesShort: ['Jan','Feb','Mar','Apr','May','June',
			'July','Aug','Sept','Oct','Nov','Dec'],
			dayNames: ['Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday'],
			dayNamesShort: ['Sun','Mon','Tue','Wed','Thu','Fri','Sat'],
			dayNamesMin: ['Su','Mo','Tu','We','Th','Fr','Sa'],
			weekHeader: 'Week',
			dateFormat: 'yy/mm/dd',
			firstDay: 0,
			isRTL: false,
			showMonthAfterYear: true,
			yearSuffix: ''};
	//set language default for calendar
	$.datepicker.setDefaults($.datepicker.regional[LANGUAGE_CURRENT]);
});