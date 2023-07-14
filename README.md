# Medical_clinic_model
A model of a medical clinic that allows you to set up patient and employee accounts, arrange and carry out visits and examinations, and also allows you to enter this information into the database.

Below is an outline of the program designed in StarUML.
![projectStarUML](https://github.com/GWeronika/medical_clinic_model/assets/126601389/e2e3f0a6-0c5a-4fc8-81e7-d40b6d5c5b16)

Program functionalities:
  - the patient can:
      - register to the clinic (checking that all data has been entered)
      - check personal information
      - schedule appointments and view them
      - view their planned examinations
      - view their referrals to specialists
      - view their prescriptions
      - view their treatment history
  - the doctor can:
      - check personal information
      - carry out a visit
      - write a referral to a specialist or for examination
      - prescribe medicines
  - the nurse can:
      - check personal information
      - carry out an examination
      - schedule appointments for patients
  - the administrator can:
      - view all clinic employees and save new employees to the database
      - remove employees from the clinic database
  - all windows after logging in have the option of returning to the previous page and logging out of the person's account
  - during login errors, the user receives a window about incorrect login, and during correct login, a window shares a message that the login is completed
