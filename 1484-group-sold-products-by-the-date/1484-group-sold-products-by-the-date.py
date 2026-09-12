import pandas as pd

def categorize_products(activities: pd.DataFrame) -> pd.DataFrame:
    
    # Remove duplicate products sold on the same date
    activities = activities.drop_duplicates()
    
    # Group by date
    result = activities.groupby('sell_date').agg(
        num_sold=('product', 'count'),
        products=('product', lambda x: ','.join(sorted(x)))
    ).reset_index()
    
    # Sort by date
    return result.sort_values('sell_date')