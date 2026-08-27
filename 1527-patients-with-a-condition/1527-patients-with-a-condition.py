import pandas as pd

def find_patients(patients: pd.DataFrame) -> pd.DataFrame:
    mask = patients["conditions"].str.split().apply(
        lambda codes: any(code.startswith("DIAB1") for code in codes)
    )

    return patients.loc[mask, ["patient_id", "patient_name", "conditions"]]