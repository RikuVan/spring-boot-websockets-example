/**
 * Login
 */
function login() {
    const username = document.getElementById('username').value;
    const url = new URL(`http://localhost:8080/login?username=${username}`);
    return fetch(url, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",
    })
        .then(function(res) {
            return res.json()
        })
        .then(function(res) {
            console.log(res);
            window.location.href = res.redirectUrl;
        });
}

/**
 * Enter to login.
 */
document.onkeydown = function (event) {
    var e = event || window.event || arguments.callee.caller.arguments[0];
    e.keyCode === 13 && login();
};