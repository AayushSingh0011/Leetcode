import pandas as pd

def rearrange_products_table(products: pd.DataFrame) -> pd.DataFrame:
    products = products.melt(
        id_vars=["product_id"],
        var_name="store",
        value_name="price"
    )

    products = products.dropna(subset=["price"])

    return products