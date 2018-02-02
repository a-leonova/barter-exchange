var apiPrefix = 'http://192.168.1.5:8080';

document.querySelector('.want').addEventListener('submit', function (event) {
    event.preventDefault();

    var formData = getFormData(event.target);
    var userId = getCookie('userId');

    console.log(formData);

    createRequest(apiPrefix + '/api/new_wish/' + userId, JSON.stringify(formData), 'POST', function(response) {
        console.log('data', formData, response);
        window.location.href = 'MyWare.html';
    });
});