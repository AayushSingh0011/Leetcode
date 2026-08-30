import pandas as pd

def department_highest_salary(employee: pd.DataFrame, department: pd.DataFrame) -> pd.DataFrame:
    
    # Join employee with department
    df = employee.merge(
        department,
        left_on="departmentId",
        right_on="id"
    )

    # Find highest salary in each department
    max_salary = df.groupby("departmentId")["salary"].transform("max")

    # Keep employees having the highest salary
    result = df[df["salary"] == max_salary]

    # Select and rename required columns
    result = result[["name_x", "salary", "name_y"]]

    result.columns = ["Employee", "Salary", "Department"]

    return result