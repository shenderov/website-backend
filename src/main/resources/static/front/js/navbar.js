window.onscroll = function() {myFunction()};
var navbar = document.getElementById("navbar");
var sticky = navbar.offsetTop;
function myFunction() {
    if (window.pageYOffset >= sticky) {
        navbar.classList.add("sticky")
    } else {
        navbar.classList.remove("sticky");
    }
}

$( document ).ready(function() {
    $('.sidenav').sidenav({
            menuWidth: 250,
            edge: 'left',
            closeOnClick: true,
            draggable: false
        }
    );
});