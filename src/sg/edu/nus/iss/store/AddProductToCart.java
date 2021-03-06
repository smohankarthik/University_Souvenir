package sg.edu.nus.iss.store;

import javax.swing.JOptionPane;

import sg.edu.nus.iss.exceptions.BadValueException;

public class AddProductToCart {
	private Cart cart = new Cart();

	public AddProductToCart() {
	}

	public AddProductToCart(Store store) {
		super();
	}
	
	public Cart getCartInstance(){
		return cart;
	}

	public Cart addProductsToCart(Product product, int quantity, Member member) throws BadValueException {

		if (product == null) {
			return null;
		} else if (product.getQuantityAvailable() < quantity) {
			JOptionPane.showMessageDialog(null, "Insuffiecient products", "Insuffiecient products",
					JOptionPane.ERROR_MESSAGE);
			return null;
		} else if (product.getQuantityAvailable() < product.getThreshold()) {
			JOptionPane.showMessageDialog(null, "Product is Running out of stock", "Product shortage ",
					JOptionPane.ERROR_MESSAGE);
			Cart c1 = cart.addCart(product, quantity, member);

			return c1;
		} else {
			Cart c1 = cart.addCart(product, quantity, member);
			return c1;
		}

	}

}
