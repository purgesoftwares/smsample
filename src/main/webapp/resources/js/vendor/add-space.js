/**
 * Positions the element in the middle of the browser window.
 * @param $element
 */
function addSpace($element) {
    var $windowHeight = $(window).height();
    var $elementHeight = $element.outerHeight();
    var $space = $('#space');

    $space.css({
        height: ($windowHeight - $elementHeight) / 2 + 'px'
    });
}
$(document).ready(function() {
    addSpace($('#main'));
});
$(window).resize(function() {
    addSpace($('#main'));
});