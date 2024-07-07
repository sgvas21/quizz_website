use QuizDB;

drop table if exists MultipleChoiceQuestionsAnswers;
drop table if exists MultipleChoiceQuestions;
drop table if exists PictureResponseQuestionsAnswers;
drop table if exists PictureResponseQuestions;
drop table if exists FillInTheBlankQuestionsAnswers;
drop table if exists FillInTheBlankQuestions;
drop table if exists ResponseQuestionsAnswers;
drop table if exists ResponseQuestions;
drop table if exists quizHistory;
drop table if exists messages;
drop table if exists requests;
drop table if exists friends;
drop table if exists quizzes;
drop table if exists users;

create table users(
                      id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
                      username varchar(50) unique not null,
                      hashedPassword varchar(200) not null,
                      isAdmin boolean not null,
                      firstName varchar(30) not null,
                      lastName varchar(30) not null
);

create table requests(
                         id int primary key auto_increment,
                         fromId int not null,
                         toId int not null,
                         foreign key(fromId) references users(id) on delete cascade,
                         foreign key(toId) references users(id) on delete cascade
);

create table friends(
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        firstFriendId INT not null,
                        secondFriendId INT not null,
                        foreign key(firstFriendId) references users(id) on delete cascade,
                        foreign key(secondFriendId) references users(id) on delete cascade,
                        UNIQUE KEY friends_unique_combination (firstFriendId, secondFriendId),
                        CHECK (firstFriendId != secondFriendId)
);

create table messages(
                         id int primary key auto_increment,
                         fromId int not null,
                         toId int not null,
                         message varchar(1000) not null,
                         sentTime timestamp not null,
                         foreign key(fromId) references users(id) on delete cascade,
                         foreign key(toId) references users(id) on delete cascade
);

create table quizzes(
                        id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
                        author INT not null,
                        quizName varchar(200) not null,
                        type varchar(200) not null,
                        foreign key(author) references users(id) on delete cascade
);

create table quizHistory (
                             id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
                             quizId INT not null,
                             userId INT not null,
                             score DOUBLE not null,
                             attemptTime timestamp not null,
                             foreign key(quizId) references quizzes(id) on delete cascade,
                             foreign key(userId) references users(id) on delete cascade
);

create table ResponseQuestions(
                                  id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
                                  question VARCHAR(800) not null,
                                  quizId INT not null,
                                  foreign key(quizId) references quizzes(id) on delete cascade
);

create table ResponseQuestionsAnswers(
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

create table FillInTheBlankQuestionsAnswers(
                                               id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
                                               answer varchar(200) not null,
                                               questionId INT not null,
                                               foreign key(questionId) references FillInTheBlankQuestions(id) on delete cascade
);

create table PictureResponseQuestions(
                                         id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
                                         question VARCHAR(800) not null,
                                         img_url varchar(1000) not null,
                                         quizId INT not null,
                                         foreign key(quizId) references quizzes(id) on delete cascade
);

create table PictureResponseQuestionsAnswers(
                                                id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
                                                answer varchar(200) not null,
                                                questionId INT not null,
                                                foreign key(questionId) references PictureResponseQuestions(id) on delete cascade
);

create table MultipleChoiceQuestions(
                                        id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
                                        question VARCHAR(800) not null,
                                        quizId INT not null,
                                        foreign key(quizId) references quizzes(id) on delete cascade
);

create table MultipleChoiceQuestionsAnswers(
                                      id INT UNIQUE PRIMARY KEY AUTO_INCREMENT,
                                      answer VARCHAR(200) not null,
                                      questionId INT not null,
                                      is_correct_answer BOOLEAN not null,
                                      foreign key(questionId) references MultipleChoiceQuestions(id) on delete cascade
);