import pandas as pd

def largest_orders(orders: pd.DataFrame) -> pd.DataFrame:
    customer = orders["customer_number"].value_counts().index[0]
    
    return pd.DataFrame({"customer_number": [customer]})