/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     3.1.2017 19:40:47                            */
/*==============================================================*/


drop table if exists Category;

drop table if exists Employee;

drop table if exists Evaluation;

drop table if exists EvaluationItem;

/*==============================================================*/
/* Table: Category                                              */
/*==============================================================*/
create table Category
(
   IdCategory           int not null,
   Description          varchar(4096) not null,
   Coefficient          int not null,
   primary key (IdCategory)
);

/*==============================================================*/
/* Table: Employee                                              */
/*==============================================================*/
create table Employee
(
   IdEmployee           int not null,
   FirstName            varchar(1024) not null,
   LastName             varchar(1024) not null,
   BirthNumber          varchar(1024) not null,
   Role                 varchar(1024) not null,
   primary key (IdEmployee)
);

/*==============================================================*/
/* Table: Evaluation                                            */
/*==============================================================*/
create table Evaluation
(
   IdEvaluation         int not null,
   IdEmployee           int not null,
   EvaluationDate       datetime,
   PlannedDate          datetime not null,
   StornoReason         varchar(4096),
   primary key (IdEvaluation)
);

/*==============================================================*/
/* Table: EvaluationItem                                        */
/*==============================================================*/
create table EvaluationItem
(
   IdEvaluationItem     int not null,
   IdCategory           int not null,
   IdEvaluation         int not null,
   Score                int not null,
   Commentary           varchar(4096),
   primary key (IdEvaluationItem)
);

alter table Evaluation add constraint FK_employee_to_evaluation foreign key (IdEmployee)
      references Employee (IdEmployee) on delete restrict on update restrict;

alter table EvaluationItem add constraint FK_category_to_evaluationitem foreign key (IdCategory)
      references Category (IdCategory) on delete restrict on update restrict;

alter table EvaluationItem add constraint FK_evaluation_to_evaluationitem foreign key (IdEvaluation)
      references Evaluation (IdEvaluation) on delete restrict on update restrict;

