import pandas as pd

def find_managers(employee: pd.DataFrame) -> pd.DataFrame:
    
    # Count direct reports for each manager
    counts = employee["managerId"].value_counts()
    
    # Managers having at least 5 direct reports
    manager_ids = counts[counts >= 5].index
    
    # Get their names
    return employee[employee["id"].isin(manager_ids)][["name"]]