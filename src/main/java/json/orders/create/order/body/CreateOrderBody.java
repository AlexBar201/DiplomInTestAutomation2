package json.orders.create.order.body;

import java.util.List;

public class CreateOrderBody {
    private List<String> ingredients;

    public CreateOrderBody(List<String> ingredients){
        this.ingredients = ingredients;
    }

    public CreateOrderBody(){}

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
