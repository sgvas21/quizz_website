<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body {
            background-color: #f0f0f0;
            transition: background-color 0.3s;
            position: relative;
            min-height: 100vh;
        }
        .card {
            border-radius: 1rem;
            background-color: #ffffff;
        }
        .card-body {
            padding: 3rem;
        }
        .card-body h3 {
            color: #003366;
        }
        .form-control {
            border-radius: 0.5rem;
        }
        .btn-primary {
            background-color: #28a745;
            border: none;
            border-radius: 0.5rem;
            transition: background-color 0.3s;
        }
        .btn-primary:hover {
            background-color: #218838;
        }
        .text-danger {
            font-weight: bold;
            color: #dc3545;
        }
        .sign-up-link {
            color: #0056b3;
            text-decoration: none;
        }
        .sign-up-link:hover {
            text-decoration: underline;
        }

        /* Dark Mode Toggle Button Styles */
        .dark-mode-toggle {
            position: fixed;
            top: -580px;
            right: -1430px;
            z-index: 1000;
        }

        .toggle {
            width: 50px;
            height: 25px;
            background: #ccc;
            border-radius: 25px;
            position: relative;
            cursor: pointer;
            transition: background-color 0.3s;
            display: inline-block;
        }
        .toggle:before {
            content: '';
            position: absolute;
            width: 25px;
            height: 25px;
            border-radius: 50%;
            background: #fff;
            top: 0;
            left: 0;
            transition: transform 0.3s;
            box-shadow: 0 0 5px rgba(0,0,0,0.2);
        }
        input:checked + .toggle {
            background-color: #6ab04c;
        }
        input:checked + .toggle:before {
            transform: translateX(25px);
        }

        /* Hide the checkbox visually */
        .visually-hidden {
            position: absolute;
            width: 1px;
            height: 1px;
            margin: -1px;
            padding: 0;
            overflow: hidden;
            clip: rect(0, 0, 0, 0);
            border: 0;
        }

        /* Dark Mode Styles */
        .dark-mode {
            background-color: #222 !important;
            color: #fff !important;
        }

        .dark-mode .card {
            background-color: #333 !important;
            color: #fff !important;
        }

        .dark-mode .card-body {
            background-color: #444 !important;
        }

        .dark-mode .card-body h3 {
            color: #66afe9 !important;
        }

        .dark-mode .form-control {
            background-color: #555 !important;
            color: #fff !important;
        }

        .dark-mode .btn-primary {
            background-color: #4da6ff !important;
            color: #fff !important;
        }

        .dark-mode .btn-primary:hover {
            background-color: #4da6ff !important;
        }

        .dark-mode .text-danger {
            color: #ff6464 !important;
        }

        .dark-mode .sign-up-link {
            color: #4da6ff !important;
        }

        .dark-mode .toggle {
            background-color: #1a73e8 !important; /* Blue color */
        }

        .dark-mode .toggle:before {
            background: #fff; /* White circle color */
        }

        .dark-mode .toggle:hover {
            background-color: #0056b3 !important; /* Darker blue on hover */
        }

        .dark-mode .toggle:before {
            box-shadow: 0 0 5px rgba(255, 255, 255, 0.2); /* Adjust shadow color */
        }

        /* Adjustments for smaller screens */
        @media (max-width: 576px) {
            .dark-mode-toggle {
                top: 10px;
                right: 10px;
            }
        }
    </style>
</head>
<body>
<%String text = (String)request.getAttribute("text");
    if(text == null) text = "";
%>
<section class="vh-100">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                <div class="card shadow-2-strong">
                    <div class="card-body text-center">
                        <h3 class="mb-5">Sign in</h3>
                        <form action="LoginServlet" method="post" class="mb-4">
                            <input required type="text" name="username" class="form-control form-control-lg mb-4" placeholder="Username" />
                            <input required type="password" name="password" class="form-control form-control-lg mb-4" placeholder="Password" />
                            <input class="btn btn-primary btn-lg btn-block" type="submit" value="Login">
                        </form>
                        <label class="text-danger"><%=text%></label>
                        <hr class="my-4">
                        <a href="register.jsp" class="sign-up-link">Sign up</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Dark Mode Toggle Button -->
<input type="checkbox" id="darkModeToggle" class="visually-hidden">
<label for="darkModeToggle" class="toggle dark-mode-toggle"></label>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script>
    // Function to toggle dark mode
    function toggleDarkMode() {
        const body = document.body;
        body.classList.toggle('dark-mode');

        // Save user preference to localStorage
        const isDarkMode = body.classList.contains('dark-mode');
        localStorage.setItem('darkMode', isDarkMode ? 'true' : 'false');
    }

    // Check localStorage for theme preference on page load
    document.addEventListener('DOMContentLoaded', function() {
        const body = document.body;
        const isDarkMode = localStorage.getItem('darkMode') === 'true';

        if (isDarkMode) {
            body.classList.add('dark-mode');
            document.getElementById('darkModeToggle').checked = true;
        }

        // Attach event listener to the dark mode toggle button
        document.getElementById('darkModeToggle').addEventListener('click', toggleDarkMode);
    });
</script>
</body>
</html>
