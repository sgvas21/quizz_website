<html lang="en">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Login</title>

    <style>
        .btn-lg {
            color: white;
        }
        .btn-block {
            background-color: #28a745;
            border-color: #28a745;
            transition: background-color 0.3s ease;
        }
        .btn-block:hover,
        .btn-block:focus {
            background-color: lightgreen; /* Darker green on hover and focus */
            border-color: lightgreen;
        }
        .btn-block:active,
        .btn-block.active {
            background-color: #28a745 !important; /* Green when pressed */
            border-color: #28a745 !important;
        }
    </style>
</head>
<body>
<section class="vh-100" style="background-color: #f0f0f0;">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                <div class="card shadow-2-strong" style="border-radius: 1rem;">
                    <div class="card-body p-5 text-center">
                        <form action="addQuizServlet" method="post" class="mb-4">
                            <input required type="text" name="name" class="form-control form-control-lg mb-4" placeholder="Enter quiz name" />
                            <input required type="number" name="nQuestions" class="form-control form-control-lg mb-4" placeholder="number of Questions" />
                            <input type="checkbox" id="random">
                            <label for="random">Randomize Questions</label>
                            <input class="btn btn-primary btn-lg btn-block" type="submit" value="Submit">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</body>
</html>
