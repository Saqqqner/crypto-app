function sortTable(column) {
    var urlParams = new URLSearchParams(window.location.search);
    var sortField = urlParams.get('sortField');
    var sortDirection = urlParams.get('sortDirection');

    if (sortField !== column) {
        urlParams.set('sortField', column);
        urlParams.set('sortDirection', 'asc');
    } else {
        if (sortDirection === 'asc') {
            urlParams.set('sortDirection', 'desc');
        } else {
            urlParams.set('sortDirection', 'asc');
        }
    }

    window.location.search = urlParams.toString();
}

function updateLimit(currentLimit) {
    var urlParams = new URLSearchParams(window.location.search);
    urlParams.set('limit', currentLimit);
    window.location.search = urlParams.toString();
}

document.addEventListener('DOMContentLoaded', function () {
    var currentLimit = new URLSearchParams(window.location.search).get('limit');
    var selectElement = document.getElementById('limitDropdown');
    selectElement.value = currentLimit;
    selectElement.addEventListener('change', function () {
        updateLimit(this.value);
    });
});