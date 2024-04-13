// -------------------------- Modal functionality. -------------------------- -----------
// Get the modal
let modalOverlay = document.querySelector(".modal-container");

function initializeModal(title) {
    // When the user clicks on <span> (x), close the modal
    document.querySelector(".modal-close").onclick = function () {
        modalOverlay.style.display = "none";
    }
    if (title) {
        document.querySelector(".modal-title").innerHTML = title;
    }
    // When the user clicks anywhere outside the modal will close it.
    window.onclick = function (event) {
        if (event.target === modalOverlay) {
            modalOverlay.style.display = "none";
        }
    }
    modalOverlay.style.display = "block";
}

//To open the MODAL from anywhere: modalOverlay.style.display = "block";
//-------------------------- Modal functionality ends ---------------------------------------
// --------------------------------------------------------------------------------------------------------

// ------When the user clicks the button, open the modal and load signin page---------
function loadSignin() {
    initializeModal("Sign In");
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementsByClassName("modal-content")[0].innerHTML = this.responseText;
        }
    };
    xhr.open("POST", "./html/signin.html", true);
    xhr.send();
}

function loadSignup() {
    initializeModal("Sign Up");
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementsByClassName("modal-content")[0].innerHTML = this.responseText;
        }
    };
    xhr.open("POST", "./html/signup.html", true);
    xhr.send();
}

//--------------------------- loginDetails -----------------------------------------------------------------
// --------------------------------------------------------------------------------------------------------

// ----------------------Form field Validation process. --------------------------
// show a message with a type of the input
function showMessage(input, message, type) {
    // get the small element and set the message
    const msg = input.parentNode.querySelector("small");
    msg.innerText = message;
    // update the class for the input
    input.className = type ? "success" : "error";
    return type;
}

function showError(input, message) {
    return showMessage(input, message, false);
}

function showSuccess(input) {
    return showMessage(input, "", true);
}

// check input field is fill or not
function hasValue(input, message) {
    if (input.value.trim() === "") {
        return showError(input, message);
    }
    return showSuccess(input);
}

// Email validation with reg-ex.
function validateEmail(input, requiredMsg, invalidMsg) {
    // check if the value is not empty
    if (!hasValue(input, requiredMsg)) {
        return false;
    }
    // validate email format
    const emailRegex =
        /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    const email = input.value.trim();
    if (!emailRegex.test(email)) {
        return showError(input, invalidMsg);
    }
    return true;
}

// ------------------Form field Validation process functionality end.--------------------------
// --------------------------------------------------------------------------------------------------------

// -----------------------send to server via POST METHOD --------------------------
function sendSignInDataToServer(form) {
    const xhr = new XMLHttpRequest();

    // Bind the FormData object and the form element
    const FD = new FormData(form);

    var data = {
        'email': FD.get('email'),
        'password': FD.get('password'),
    };
    console.log(data);
    console.log(FD.keys().next());

    var url = window.location + "signin";
    console.log(url);

    // Define what happens on successful data submission
    xhr.addEventListener("load", (event) => {
        document.querySelector("#a-result").style.display = "block";
        document.querySelector("#a-result").innerHTML = event.target.responseText;

        //To refresh the window
        window.onclick = function (event) {
            if (event.target === modalOverlay) {
                modalOverlay.style.display = "none";
                window.location.replace(window.location.origin + '/shareapp');
            }
        }
    });
    xhr.addEventListener("error", (event) => {
        document.querySelector("#a-result").innerHTML = "<p style='color: darkred'>Server Error</p>";
    });
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(new URLSearchParams(data).toString());
}

function processSignin() {
    console.log("Form Loaded");

    const form = document.querySelector("#signin-form");

    form.addEventListener("submit", function (event) {
        console.log("Form Event Listener set")

        // stop form submission
        event.preventDefault();

        const EMAIL_REQUIRED = "Please enter your email";
        const EMAIL_INVALID = "Please enter a correct email address format";
        const PASS_REQUIRED = "Please enter password";

        // validate the form
        let emailValid = validateEmail(form.elements["email"], EMAIL_REQUIRED, EMAIL_INVALID);
        let passValid = hasValue(form.elements["password"], PASS_REQUIRED);

        // if valid, submit the form.
        if (emailValid && passValid) {
            sendSignInDataToServer(form);
        }
    });
}

// ------------------Sign-in field Validation and submit process ends. --------------------------
// --------------------------------------------------------------------------------------------------------

// ------------------------Sign up validation and submit process--------------------------
function sendSignUpDataToServer(form) {
    const xhr = new XMLHttpRequest();

    // Bind the FormData object and the form element
    const FD = new FormData(form);

    var data = {
        'fname': FD.get('fname'),
        'lname': FD.get('lname'),
        'email': FD.get('email'),
        'phone': FD.get('phone'),
        'password': FD.get('password'),
    };
    console.log(data);

    var url = window.location + "signup";
    console.log(url);

    // Define what happens on successful data submission
    xhr.addEventListener("load", (event) => {
        document.querySelector("#a-result").style.display = "block";
        document.querySelector("#a-result").innerHTML = event.target.responseText;

        //To refresh the window after the process
        window.onclick = function (event) {
            if (event.target == modal) {
                modalOverlay.style.display = "none";
                window.location.replace(window.location.origin + '/shareapp');
            }
        }
    });
    xhr.addEventListener("error", (event) => {
        document.querySelector('#a-result').innerHTML = "<p style='color: darkred'>Server Error</p>";
    });
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(new URLSearchParams(data).toString());
}

//validate and submit to server
function processSignup() {
    console.log("Form Loaded");

    const form = document.getElementById("signup-a");

    form.addEventListener("submit", function (event) {
        console.log("Form Event Listener set")

        // stop form submission
        event.preventDefault();

        const FNAME_REQUIRED = "Please enter your first name";
        const LNAME_REQUIRED = "Please enter your last name";
        const PHONE_REQUIRED = "Please enter your phone number";
        const EMAIL_REQUIRED = "Please enter your email";
        const EMAIL_INVALID = "Please enter a correct email address format";
        const PASS_REQUIRED = "Please enter password";

        // validate the form
        let fnameValid = hasValue(form.elements["fname"], FNAME_REQUIRED);
        let lnameValid = hasValue(form.elements["lname"], LNAME_REQUIRED);
        let phoneValid = hasValue(form.elements["phone"], PHONE_REQUIRED);
        let emailValid = validateEmail(form.elements["email"], EMAIL_REQUIRED, EMAIL_INVALID);
        let passValid = hasValue(form.elements["password"], PASS_REQUIRED);

        // if valid, submit the form.
        if (fnameValid && lnameValid && phoneValid && emailValid && passValid) {
            sendSignUpDataToServer(form);
        }
    });
}
// -----------------Sign up validation and submit process ends --------------------
// --------------------------------------------------------------------------------------------------------


// -----------------Sign-out functionality by post method -------------------------- -------
function signOut() {
    const xhr = new XMLHttpRequest();
    xhr.open('post', './link?rqst=signout');
    xhr.setRequestHeader("Content-Type", "text/plain");
    xhr.send("sending something!");
    xhr.onload = function () {
        window.location.pathname.replace(window.location.origin + '/shareapp');
    };
}

// ------------------Sign-out functionality by post method ends --------------------------
// --------------------------------------------------------------------------------------------------------

//Show signin details on click Profile photo---------------------------------------
function signinDetails() {
    loginDetailsModal = document.getElementsByClassName("logindetail-container")[0];
    loginDetailsModal.style.display = "block";
    var closeBtn = document.getElementsByClassName("logindetail-close")[0];
    closeBtn.onclick = function () {
        loginDetailsModal.style.display = "none";
    }
    window.onclick = function (event) {
        if (event.target == loginDetailsModal) {
            loginDetailsModal.style.display = "none";
        }
    }
}


//load profile on the modal for editing or viewing details
function loadProfile() {
    initializeModal("Profile");
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementsByClassName("modal-content")[0].innerHTML = this.responseText;
        }
    };
    xhr.open("POST", "./jsp/LoadProfile.jsp", true);
    xhr.send();
}

function sendUpdateData(form) {
    const xhr = new XMLHttpRequest();

    // Bind the FormData object and the form element
    const formData = new FormData(form);

    let url = window.location + "updateprofile";
    console.log(url);

    // Event set on successful data submission
    xhr.addEventListener("load", (event) => {
        document.getElementById("updstatus").style.display = "block";
        document.getElementById("updstatus").innerHTML = event.target.responseText;
    });

    // Event set in case of error
    xhr.addEventListener("error", (event) => {
        document.getElementById('updstatus').innerHTML = "Oops! Something went wrong.";
    });

    // Set up our request & send
    xhr.open("POST", url, true);
    xhr.send(formData);
}

function editProfile() {
    Array.from(document.querySelectorAll(".dis"))
        .forEach(element => element.removeAttribute("disabled"));

    // Get the form element
    const form = document.getElementById('upd-form');
    // Add 'submit' event handler
    form.addEventListener("submit", (event) => {
        event.preventDefault();
        sendUpdateData(form);
    });
}

// ----------------- Pop functionality end ---------------------------------------
// --------------------------------------------------------------------------------------------------------

// ----------------- Sidebar functionality Starts ---------------------------------
// sidebar open/close functionality on click the left corner button on the page
function toggleNav() {
    let val = getComputedStyle(document.getElementsByClassName("side-bar")[0]).display;
    // console.log(getComputedStyle(document.getElementsByClassName("side-bar")[0].width));
    if (val == "block") {
        document.getElementsByClassName("side-bar")[0].style.display = "none";
    } else {
        document.getElementsByClassName("side-bar")[0].style.display = "block";
    }
}

//Setting buttons active by click
const header = document.querySelector(".side-bar");
const btns = header.querySelector(".nav-link");
for (let i = 0; i < btns.length; i++) {
    btns[i].addEventListener("click", function () {
        var current = document.getElementsByClassName("active");
        current[0].className = current[0].className.replace(" active", "");
        this.className += " active";
    });
}

//Show my post
function myPosts() {

}

//Load videos in the content panel
window.onload = function () {
    loadVideos();
}

function loadVideos() {
    console.log("loading videos..");
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementsByClassName("videos")[0].innerHTML = this.responseText;
        }
    };
    xhr.open("POST", "postretrieveall", true);
    xhr.send();
}

function loadAudios() {
}

function loadBlogs() {
}

function loadHistory() {
}

function loadLikedItems() {
}

// load the post creator on press upload
function openPostCreatorWindow() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementsByClassName("videos")[0].innerHTML = this.responseText;
        }
    };
    xhr.open("POST", "./jsp/CreatePost.jsp", true);
    xhr.send();
}

function goHome() {
    window.location.replace(window.location.origin + '/shareapp');
}


function uploadPost() {
    console.log("in uploadPostmtd");
    // define URL and for element
    const url = "/postupload";
    // collect files
    const files = document.querySelector('[name=post-file]').files;
    const formData = new FormData(document.querySelector('#create-post-form'));
    formData.append('file', files[0]);
    console.log("in uploadPostmtd");

    // post form data
    const xhr = new XMLHttpRequest();

    // log response
    xhr.onload = () => {
        console.log(xhr.responseText)
    }

    // create and send the reqeust
    xhr.open('POST', url)
    xhr.send(formData)
}

// ------------------------Sidebar functionality Ends-----------------------------------------------------
// --------------------------------------------------------------------------------------------------------
