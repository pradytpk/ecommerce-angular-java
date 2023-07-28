import { Product } from "src/app/common/product";
import { ProductCategory } from "src/app/common/product-category";

export interface GetResponseProduct {
  content: Product[];
}
export interface GetResponseProductCategory {
  content: ProductCategory[];
}
