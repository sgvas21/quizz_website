use databaza;

drop table if exists MultipleChoiceQuestions;
drop table if exists MultipleChoiceQuestionAnswers;
drop table if exists FillInTheBlankAnswer;
drop table if exists fillintheblankquestions;
drop table if exists ResponseQuestionsAnswer;
drop table if exists ResponseQuestions;
drop table if exists quizzes;
drop table if exists users;

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

create table MultipleChoiceQuestions(
                                        id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
                                        question VARCHAR(255) not null,
                                        quiz_id VARCHAR(255) not null
);

create table MultipleChoiceQuestionAnswers(
                                              id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
                                              question_id INT not null,
                                              answer VARCHAR(255) not null,
                                              is_correct_answer BOOLEAN not null
);

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

create table FillInTheBlankQuestions(
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