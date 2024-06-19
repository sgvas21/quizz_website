create database IF NOT EXISTS databaza;

use databaza;

create table MultipleChoiceQuestions(
                                        id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
                                        question VARCHAR(255) UNIQUE not null,
                                        correctAnswer VARCHAR(255) not null
);

create table MultipleChoiceQuestionsWrongAnswers(
                                        id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
                                        questionId INT not null,
                                        incorrectAnswer VARCHAR(255) not null,
                                        CONSTRAINT fk_questionId FOREIGN KEY(questionId) REFERENCES MultipleChoiceQuestions(id)
);