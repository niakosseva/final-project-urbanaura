// document.addEventListener('DOMContentLoaded', function () {
//     const modal = document.querySelector('.js-modal1');
//     const productName = modal.querySelector('.js-product-name');
//     const productPrice = modal.querySelector('.js-product-price');
//     const productDescription = modal.querySelector('.js-product-description');
//     const productImage = modal.querySelector('.js-product-image');
//     const addToCartButton = modal.querySelector('.js-addcart-detail');
//     const targetCurrencySelect = document.getElementById('targetCurrency');
//     let exchangeRates = {}; // Store exchange rates for conversions
//
//     // Set up event listener for 'Quick View' buttons
//     document.querySelectorAll('.js-show-modal1').forEach(button => {
//         button.addEventListener('click', function () {
//             const name = this.getAttribute('data-name');
//             const price = parseFloat(this.getAttribute('data-price'));
//             const description = this.getAttribute('data-description');
//             const image = this.getAttribute('data-image');
//             const itemId = this.getAttribute('data-id');
//
//             // Update modal content with product details
//             productName.textContent = name;
//             productPrice.textContent = `$${price.toFixed(2)}`;
//             productDescription.textContent = description;
//             productImage.setAttribute('src', image);
//
//             // Set data attributes for 'Add to Cart' button
//             addToCartButton.setAttribute("data-id", itemId);
//             addToCartButton.setAttribute("data-name", name);
//             addToCartButton.setAttribute("data-price", price);
//
//             // Show modal
//             modal.classList.add('show-modal1');
//
//             // Fetch and apply exchange rates if currency is selected
//             fetchExchangeRates().then(() => {
//                 const targetCurrency = targetCurrencySelect.value;
//                 if (targetCurrency && targetCurrency !== "USD") {
//                     convertPrice(price, targetCurrency);
//                 }
//             });
//         });
//     });
//
//     // Function to fetch exchange rates
//     function fetchExchangeRates() {
//         return fetch('https://v6.exchangerate-api.com/v6/2bcc319483b74b524bdd3cbe/latest/USD')
//             .then(response => response.json())
//             .then(data => {
//                 exchangeRates = data.rates;
//                 populateCurrencyDropdown(exchangeRates);
//             })
//             .catch(error => console.error('Error fetching exchange rates:', error));
//     }
//
//     // Populate currency dropdown
//     function populateCurrencyDropdown(rates) {
//         while (targetCurrencySelect.options.length > 1) {
//             targetCurrencySelect.removeChild(targetCurrencySelect.options[1]);
//         }
//         for (const currency in rates) {
//             const option = document.createElement('option');
//             option.value = currency;
//             option.textContent = currency;
//             targetCurrencySelect.appendChild(option);
//         }
//     }
//
//     // Convert price based on selected currency
//     function convertPrice(originalPrice, targetCurrency) {
//         const convertedPrice = originalPrice * (exchangeRates[targetCurrency] || 1);
//         productPrice.textContent = `${convertedPrice.toFixed(2)} ${targetCurrency}`;
//     }
//
//     // Close modal
//     document.querySelectorAll('.js-hide-modal1').forEach(closeBtn => {
//         closeBtn.addEventListener('click', () => modal.classList.remove('show-modal1'));
//     });
// });
