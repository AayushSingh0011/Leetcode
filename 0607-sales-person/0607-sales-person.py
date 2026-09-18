import pandas as pd

def sales_person(
    sales_person: pd.DataFrame,
    company: pd.DataFrame,
    orders: pd.DataFrame
) -> pd.DataFrame:

    # Get the company ID(s) of RED
    red_company = company[company["name"] == "RED"]

    # Get salespersons who made orders for RED
    red_sales = orders[
        orders["com_id"].isin(red_company["com_id"])
    ]["sales_id"]

    # Keep salespersons who are NOT in the RED sales list
    return sales_person[
        ~sales_person["sales_id"].isin(red_sales)
    ][["name"]]