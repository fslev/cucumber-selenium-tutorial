@ui
@local @prod
@department
Feature: Department feature

  Scenario: Add and remove departments
    * Go to departments tab
    * Check department list size=6
    * Add department with name=My Department 1
    * Add department with name=My Department 2
    * Check department list size=8
    * Remove department with name=Frozen
    * Check department list size=7
