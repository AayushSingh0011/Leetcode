import pandas as pd

def nth_highest_salary(employee: pd.DataFrame, N: int) -> pd.DataFrame:
    column = f"getNthHighestSalary({N})"

    salaries = employee["salary"].drop_duplicates()
    salaries = salaries.sort_values(ascending=False)

    if N <= 0 or N > len(salaries):
        return pd.DataFrame({column: [None]})

    return pd.DataFrame({
        column: [salaries.iloc[N - 1]]
    })