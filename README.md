# Hitit GitHub API Project
This project uses GitHub API v3 to write the 10 most committing users to a text file for each of the 5 most downloaded repos belonging to the apache organization (https://github.com/apache)

## To-do
- [x] dto
    - [x] Contributors
    - [x] Users
- [x] services
  - [x] ContributorService
    - [x] findAndWriteTopContributors()
    - [x] findUser(String username)
    - [x] writeTopContributorsToFile()

### Running the Project
* The project's java codes change compilation effects to jar with JDK 1.8. Simply run run.bat (github-api-project2\run.bat) to run the project.
* After running, file.txt is output to the project directory (github-api-project2\). In this resulting file, the information of the 10 users who have committed at most 5 repos are displayed.

### Project Working Steps
* After the project is running, enter the apache organization from the GitHub Repo service (https://api.github.com/repos/apache/) with the findAndWriteTopContributors method in the ContributorService service ["ecarts", "superset", "dubbo", "spark", "airflow] takes the 10 users who commit the most to their repository.
* The location, company and contributions information of the user are retrieved from the GitHub User service (https://api.github.com/users/) with the findUser method in the ContributorService service of these imported users.
* The collected repository and user information are written to the file.txt file with the writeTopContributorsToFile method.
