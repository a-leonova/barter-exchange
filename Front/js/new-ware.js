var apiPrefix = 'http://192.168.1.5:8080';
console.log('hgxfbkjn', 1);
document.querySelector('.main_post_form').addEventListener('submit', function (event) {
    event.preventDefault();

    var formData = getFormData(event.target);
    var userId = getCookie('userId');

    console.log(formData);

    createRequest(apiPrefix + '/api/new_ware/' + userId, JSON.stringify(formData), 'POST', function(response) {
        console.log('data', formData, response);
        window.location.href = 'MyWare.html';
    });
});