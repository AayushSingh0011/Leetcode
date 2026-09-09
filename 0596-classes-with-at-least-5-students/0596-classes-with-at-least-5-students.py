import pandas as pd

def find_classes(courses: pd.DataFrame) -> pd.DataFrame:
    count = courses.groupby("class").size().reset_index(name="count")
    
    return count[count["count"] >= 5][["class"]]