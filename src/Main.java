import java.util.Scanner;
// Semyon Galitsky - 213863319
// Dor Mendelovich - 214289613

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice, count, wage, id, title;
        String lecturerName, degreeName, committeeName, departmentName;

        System.out.println("Please provide the college name: ");
        College college = new College(scanner.nextLine());
        System.out.println("Starting system for: " + college.getName());

        do {
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("                                      MENU                                       ");
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("[0] - Exit");
            System.out.println("[1] - Add lecturer / Dr / Professor");
            System.out.println("[2] - Add committee");
            System.out.println("[3] - Assign lecturer to committee");
            System.out.println("[4] - Update head of committee");
            System.out.println("[5] - Remove lecturer from a committee");
            System.out.println("[6] - Add study department");
            System.out.println("[7] - Assign lecturer to department");
            System.out.println("[8] - Show salary average of all college lecturers");
            System.out.println("[9] - Show salary average of all college lecturers in a department");
            System.out.println("[10]- Show all lecturers");
            System.out.println("[11]- Show all committees");
            System.out.println("[12]- Add article to Dr / Professor");
            System.out.println("[13]- Compare Dr / Professors by articles count");
            System.out.println("[14]- Compare committees (by members or total articles)");
            System.out.println("[15]- Duplicate a committee");
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("\nPlease select an action: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // buffer

            try {
                switch (choice) {
                    case 0:
                        System.out.println("Exit selected, shutting system down.");
                        break;

                    case 1:
                        while (true) {
                            System.out.print("Please provide a name: ");
                            lecturerName = scanner.nextLine();
                            if (lecturerName.length() >= 3) {
                                break;
                            }
                            System.out.println("[Error] Name must be at least 3 characters long. Please try again.");
                        }

                        while (true) {
                            System.out.print("Please provide an id (9 digits): ");
                            id = scanner.nextInt();
                            scanner.nextLine(); // buffer

                            if (id < 100000000 || id > 999999999) {
                                System.out.println("[Error] ID must be exactly 9 digits. Please try again.");
                                continue;
                            }

                            if (college.getLecturerById(id) != null) {
                                System.out.println("[Error] A lecturer with this ID already exists. Please enter a different ID.");
                                continue;
                            }
                            break;
                        }

                        while (true) {
                            System.out.println("Please provide Title level [1-4]: ");
                            System.out.println("    [1] - Bachelors\n    [2] - Masters\n    [3] - Dr\n    [4] - Professor");
                            title = scanner.nextInt();
                            scanner.nextLine(); // buffer

                            if (title < 1 || title > 4) {
                                System.out.println("[Error] Invalid title level. Must be between 1 and 4.");
                                continue;
                            }
                            break;
                        }

                        System.out.print("Please provide name of Degree: ");
                        degreeName = scanner.nextLine();

                        while (true) {
                            System.out.print("Please provide wage: ");
                            wage = scanner.nextInt();
                            scanner.nextLine(); // buffer

                            if (wage < 0) {
                                System.out.println("[Error] Wage must be positive.");
                                continue;
                            }
                            break;
                        }

                        Lecturer newLecturer;
                        if (title == 1 || title == 2) {
                            newLecturer = new Lecturer(lecturerName, id, title, degreeName, wage);
                        } else if (title == 3) {
                            newLecturer = new Dr(lecturerName, id, degreeName, wage);
                        } else {
                            System.out.print("Please provide awarding institution for the professor: ");
                            String institution = scanner.nextLine();
                            newLecturer = new Professor(lecturerName, id, degreeName, wage, institution);
                        }

                        college.addLecturer(newLecturer);
                        System.out.println("[Success] Lecturer " + lecturerName + " was successfully added.");
                        break;

                    case 2:
                        while (true) {
                            System.out.print("Please provide the name of the committee you wish to add: ");
                            committeeName = scanner.nextLine();

                            if (college.getCommitteeByName(committeeName) == null) {
                                break;
                            }
                            System.out.println("[Error] Committee name already exists. Please choose a different name.");
                        }

                        while (true) {
                            System.out.print("Please provide the name of the head of the committee: ");
                            lecturerName = scanner.nextLine();

                            Lecturer head = college.getLecturerByName(lecturerName);
                            if (head == null) {
                                System.out.println("[Error] Lecturer not found. Please try again.");
                                continue;
                            }
                            if (!(head instanceof Dr)) {
                                System.out.println("[Error] Head of committee must be at least a Dr. Please try again.");
                                continue;
                            }
                            break;
                        }

                        college.addCommittee(committeeName, lecturerName);
                        System.out.println("[Success] Committee " + committeeName + " was successfully added.");
                        break;

                    case 3:
                        System.out.print("Please provide the name of the lecturer: ");
                        lecturerName = scanner.nextLine();
                        System.out.print("Please provide the name of the committee: ");
                        committeeName = scanner.nextLine();

                        college.assignLecturerToCommittee(lecturerName, committeeName);
                        System.out.println("[Success] Assigned successfully.");
                        break;

                    case 4:
                        System.out.print("Please provide the name of the lecturer: ");
                        lecturerName = scanner.nextLine();
                        System.out.print("Please provide the name of the committee: ");
                        committeeName = scanner.nextLine();

                        college.updateHeadOfCommittee(lecturerName, committeeName);
                        System.out.println("[Success] Head of committee updated successfully.");
                        break;

                    case 5:
                        System.out.print("Please provide the name of the lecturer: ");
                        lecturerName = scanner.nextLine();
                        System.out.print("Please provide the name of the committee: ");
                        committeeName = scanner.nextLine();

                        college.removeLecturerFromCommittee(lecturerName, committeeName);
                        System.out.println("[Success] Lecturer removed from committee.");
                        break;

                    case 6:
                        while (true) {
                            System.out.print("Please provide the name of the department you wish to add: ");
                            departmentName = scanner.nextLine();

                            if (college.getDepartmentByName(departmentName) != null) {
                                System.out.println("[Error] Department name already exists. Please choose a different name.");
                                continue;
                            }
                            break;
                        }

                        while (true) {
                            System.out.print("Please provide the student count: ");
                            count = scanner.nextInt();
                            scanner.nextLine(); // buffer

                            if (count < 0) {
                                System.out.println("[Error] Student count cannot be negative. Please try again.");
                                continue;
                            }
                            break;
                        }

                        college.addDepartment(departmentName, count);
                        System.out.println("[Success] Department " + departmentName + " was successfully added.");
                        break;

                    case 7:
                        System.out.print("Please provide the name of the lecturer: ");
                        lecturerName = scanner.nextLine();
                        System.out.print("Please provide the name of the department: ");
                        departmentName = scanner.nextLine();

                        college.assignLecturerToDepartment(lecturerName, departmentName);
                        System.out.println("[Success] Lecturer assigned to department.");
                        break;

                    case 8:
                        System.out.println("The average wage of all lecturers across the college is: " + college.getAverageWage());
                        break;

                    case 9:
                        System.out.print("Please provide department name: ");
                        departmentName = scanner.nextLine();
                        double avg = college.getDepartmentAverageWage(departmentName);
                        System.out.println("The average wage of lecturers in " + departmentName + " is: " + avg);
                        break;

                    case 10:
                        String lecturersDetails = college.getAllLecturersDetails();
                        if (lecturersDetails.isEmpty()) {
                            System.out.println("[Error] No lecturers found.");
                        } else {
                            System.out.println(lecturersDetails);
                        }
                        break;

                    case 11:
                        String committeesDetails = college.getAllCommitteesDetails();
                        if (committeesDetails.isEmpty()) {
                            System.out.println("[Error] No committees found.");
                        } else {
                            System.out.println(committeesDetails);
                        }
                        break;

                    case 12:
                        college.checkMinimumDoctors(1);

                        Lecturer lecturer;
                        while (true) {
                            System.out.println("Provide the name of the lecturer: ");
                            lecturerName = scanner.nextLine();
                            lecturer = college.getLecturerByName(lecturerName);

                            if (lecturer == null) {
                                System.out.println("[Error] Lecturer not found. Please try again.");
                                continue;
                            }
                            if (!(lecturer instanceof Dr)) {
                                System.out.println("[Error] Lecturer " + lecturerName + " is not a Dr. or Professor. Cannot add articles. Please try again.");
                                continue;
                            }
                            break;
                        }

                        System.out.println("Provide the name of article: ");
                        String articleName = scanner.nextLine();

                        ((Dr) lecturer).addArticle(articleName);
                        System.out.println("[Success] Article added successfully to " + lecturerName + ".");
                        break;

                    case 13:
                        college.checkMinimumDoctors(2);

                        String firstName, secondName;
                        Lecturer firstLecturer, secondLecturer;

                        while (true) {
                            System.out.println("Provide the name of the first lecturer you want to compare: ");
                            firstName = scanner.nextLine();
                            firstLecturer = college.getLecturerByName(firstName);

                            if (!(firstLecturer instanceof Dr)) {
                                System.out.println("[Error] Lecturer not found or is not at least a Doctor. Please try again.");
                                continue;
                            }
                            break;
                        }

                        while (true) {
                            System.out.println("Provide the name of the second lecturer you want to compare: ");
                            secondName = scanner.nextLine();

                            if (secondName.equals(firstName)) {
                                System.out.println("[Error] Cannot compare a lecturer to themselves. Please choose a different one.");
                                continue;
                            }

                            secondLecturer = college.getLecturerByName(secondName);

                            if (!(secondLecturer instanceof Dr)) {
                                System.out.println("[Error] Lecturer not found or is not at least a Doctor. Please try again.");
                                continue;
                            }
                            break;
                        }

                        Dr dr1 = (Dr) firstLecturer;
                        Dr dr2 = (Dr) secondLecturer;

                        int articlesDifference = dr1.compareTo(dr2);

                        if (articlesDifference > 0) {
                            System.out.println(firstName + " has " + articlesDifference + " more articles than " + secondName + ".");
                        } else if (articlesDifference < 0) {
                            System.out.println(secondName + " has " + -(articlesDifference) + " more articles than " + firstName + ".");
                        } else {
                            System.out.println("Both " + firstName + " and " + secondName + " have the exact same amount of articles.");
                        }
                        break;

                    case 14:
                        college.checkMinimumCommittees(2);

                        String firstCommitteeName, secondCommitteeName;
                        Committee firstCommittee, secondCommittee;

                        while (true) {
                            System.out.println("Provide the name of the first Committee you want to compare: ");
                            firstCommitteeName = scanner.nextLine();
                            firstCommittee = college.getCommitteeByName(firstCommitteeName);

                            if (firstCommittee == null){
                                System.out.println("[Error] Committee not found. Please try again.");
                                continue;
                            }
                            break;
                        }

                        while (true) {
                            System.out.println("Provide the name of the second Committee you want to compare: ");
                            secondCommitteeName = scanner.nextLine();

                            if (secondCommitteeName.equals(firstCommitteeName)) {
                                System.out.println("[Error] Cannot compare a committee to itself. Please choose a different one.");
                                continue;
                            }

                            secondCommittee = college.getCommitteeByName(secondCommitteeName);

                            if (secondCommittee == null){
                                System.out.println("[Error] Committee not found. Please try again.");
                                continue;
                            }
                            break;
                        }

                        System.out.println("---------------------------------\n" +
                                "[1] Compare by members.\n" +
                                "[2] Compare by sum of articles.\n" +
                                "---------------------------------");

                        int option = scanner.nextInt();
                        scanner.nextLine(); //buffer

                        while (option < 1 || option > 2) {
                            System.out.println("--- Action Failed ---\nInput out of range. Must be 1 or 2.");
                            System.out.println("Please select a valid input: ");
                            option = scanner.nextInt();
                            scanner.nextLine(); //buffer
                        }

                        int comparisonResult;
                        String comparisonType;

                        if (option == 1) {
                            CompareCommitteeByMembers compByMembers = new CompareCommitteeByMembers();
                            comparisonResult = compByMembers.compare(firstCommittee, secondCommittee);
                            comparisonType = "members";
                        } else {
                            CompareCommitteeByArticles compByArticles = new CompareCommitteeByArticles();
                            comparisonResult = compByArticles.compare(firstCommittee, secondCommittee);
                            comparisonType = "articles";
                        }

                        if (comparisonResult > 0) {
                            System.out.println("Committee '" + firstCommitteeName + "' is greater than '" + secondCommitteeName + "' in terms of " + comparisonType + ".");
                        } else if (comparisonResult < 0) {
                            System.out.println("Committee '" + secondCommitteeName + "' is greater than '" + firstCommitteeName + "' in terms of " + comparisonType + ".");
                        } else {
                            System.out.println("Both committees are equal in terms of " + comparisonType + ".");
                        }
                        break;

                    case 15:
                        college.checkMinimumCommittees(1);
                        while (true) {
                            System.out.println("Provide the name of the committee you want to clone: ");
                            committeeName = scanner.nextLine();

                            if (college.getCommitteeByName(committeeName) == null) {
                                System.out.println("[Error] Committee not found. Please try again.");
                                continue;
                            }
                            break;
                        }

                        college.cloneCommittee(committeeName);
                        System.out.println("Committee " + committeeName + " was duplicated successfully.");
                        break;

                    default:
                        System.out.println("--- Action Failed ---\nInput out of range. Must be between 0 and 15.");
                }
            } catch (MemberAlreadyInCommitteeException e) {
                System.out.println("--- Action Failed (Committee Membership Error) ---\n" + e.getMessage());
            } catch (ManagementException e) {
                System.out.println("--- Action Failed (System Error) ---\n" + e.getMessage());
            } catch (CloneNotSupportedException e) {
                System.out.println("--- Action Failed (Cloning Error) ---\n" + e.getMessage());
            } catch (Exception e) {
                System.out.println("--- Unexpected Error ---\n" + e.getMessage());
            }

            if (choice != 0) {
                System.out.println("\nPress enter to continue");
                scanner.nextLine();
            }

        } while (choice != 0);

        scanner.close();
    }
}