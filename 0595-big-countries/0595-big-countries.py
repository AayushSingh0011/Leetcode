import pandas as pd

def big_countries(world: pd.DataFrame) -> pd.DataFrame:
    big = world[
        (world['area']>=3000000) |
        (world['population']>=25000000)
    ]
    return big[["name","population","area"]]