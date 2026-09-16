import pandas as pd

def students_and_examinations(
    students: pd.DataFrame,
    subjects: pd.DataFrame,
    examinations: pd.DataFrame
) -> pd.DataFrame:

    # Create every student-subject combination
    students["key"] = 1
    subjects["key"] = 1

    result = students.merge(subjects, on="key").drop(columns="key")

    # Count examinations for each student-subject pair
    exam_count = (
        examinations
        .groupby(["student_id", "subject_name"])
        .size()
        .reset_index(name="attended_exams")
    )

    # Add exam counts
    result = result.merge(
        exam_count,
        on=["student_id", "subject_name"],
        how="left"
    )

    # If no exam, count = 0
    result["attended_exams"] = result["attended_exams"].fillna(0).astype(int)

    # Required columns and sorting
    result = result[
        ["student_id", "student_name", "subject_name", "attended_exams"]
    ]

    return result.sort_values(
        ["student_id", "subject_name"]
    ).reset_index(drop=True)