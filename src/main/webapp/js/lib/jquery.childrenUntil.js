(function($) {
	// BFS until we find a tier with matched nodes
	$.fn.childrenUntil = function(selector) {
		var matches = [];
		if (selector && selector != '') {
			var queue = [];
			queue.push(this);
			while(matches.length == 0 && queue.length > 0) {
				var node = queue.shift();
				$.each(node.children(), function(i,e) {
					var child = $(e);
					queue.push(child);
					if (child.is(selector)) matches.push(e);
				});
			}
		}
		return matches;
	};
})(jQuery);