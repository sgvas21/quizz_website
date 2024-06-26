create database IF NOT EXISTS databaza;

use databaza;

create table users(
    id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
    username varchar(50) unique not null,
    hashedPassword varchar(200) not null,
    isAdmin boolean not null,
    firstName varchar(30) not null,
    lastName varchar(30) not null
);

create table quizzes(
    id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
    author INT not null,
    quizName varchar(200) not null,
    foreign key(author) references users(id) on delete cascade
);

# create table MultipleChoiceQuestions(
#                                         id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
#                                         question VARCHAR(255) UNIQUE not null,
#                                         correctAnswer VARCHAR(255) not null
# );
#
# create table MultipleChoiceQuestionsWrongAnswers(
#                                         id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
#                                         questionId INT not null,
#                                         incorrectAnswer VARCHAR(255) not null,
#                                         CONSTRAINT fk_questionId FOREIGN KEY(questionId) REFERENCES MultipleChoiceQuestions(id)
# );

create table ResponseQuestions(
    id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
    question VARCHAR(800) not null,
    quizId INT not null,
    foreign key(quizId) references quizzes(id) on delete cascade
);

create table ResponseQuestionsAnswer(
    id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
    answer VARCHAR(200) not null,
    questionId INT not null,
    foreign key(questionId) references ResponseQuestions(id) on delete cascade
);

create table FillInTheBlankQuestion(
    id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
    question VARCHAR(800) not null,
    quizId INT not null,
    foreign key(quizId) references quizzes(id) on delete cascade
);

create table FIllInTheBlankAnswer(
    id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
    answer varchar(200) not null,
    questionId INT not null,
    foreign key(questionId) references fillintheblankquestions(id) on delete cascade
);