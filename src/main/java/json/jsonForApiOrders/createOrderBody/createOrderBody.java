package json.jsonForApiOrders.createOrderBody;

import java.util.List;

public class createOrderBody {
    private List<String> ingredients;

    public createOrderBody(List<String> ingredients){
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
