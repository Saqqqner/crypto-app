function redirectToComment(element) {
    const cryptoName = element.getAttribute('data-crypto-name');
    const commentId = element.getAttribute('data-id');
    window.location.href = `/user-api/crypto/${cryptoName}/comments/${commentId}`;
}

function toggleFavourite(element, add) {
    const cryptoName = element.getAttribute('data-crypto-name');
    const url = add ? `/user-api/crypto/${cryptoName}/add-to-favourites` : `/user-api/crypto/${cryptoName}/remove-from-favourites`;
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-CSRF-TOKEN': document.querySelector('input[name="_csrf"]').value
        }
    }).then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert('Ошибка при изменении статуса избранного');
        }
    }).catch(error => {
        console.error('Ошибка при изменении статуса избранного:', error);
    });
}


