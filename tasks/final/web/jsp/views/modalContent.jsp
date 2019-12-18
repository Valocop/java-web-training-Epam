<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 16.12.2019
  Time: 23:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    var modal = document.getElementById('modal-wrapper');
    var boolReg = `${pageContext.findAttribute("user.registration")}`;
    var boolLogin = `${pageContext.findAttribute("user.login.exception")}`;
    var toast = `${pageContext.findAttribute("toast")}`;

    if (boolReg) {
        modal.style.display = "block";
        setTimeout(() => {
            var registrationButton = document.querySelector('#registration > span');
            registrationButton.click();
        }, 60);
    }

    if (boolLogin) {
        modal.style.display = "block";
        setTimeout(() => {
            var registrationButton = document.querySelector('#login > span');
            registrationButton.click();
        }, 60);
    }

    window['counter'] = 0;
    var snackbarContainer = document.querySelector('#demo-toast-example');
    var showToastButton = document.querySelector('#demo-show-toast');

    showToastButton.addEventListener('click', function () {
        setTimeout(() => {
            var data = {
                message: toast
            };
            snackbarContainer.MaterialSnackbar.showSnackbar(data);
        }, 1000);
    });

    if (toast) {
        showToastButton.click();
    }
</script>
