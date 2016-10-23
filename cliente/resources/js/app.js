angular.module('FullstackJavaTesteApp', [
  'FullstackJavaTesteApp.services',
  'FullstackJavaTesteApp.controllers',
  'ngRoute'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.
  	when("/", {templateUrl: "partials/home.html", controller: "homeController"}).
	when("/produtos", {templateUrl: "partials/produtos.html", controller: "produtosController"}).
	when("/produtos/novo", {templateUrl: "partials/produto.html", controller: "novoProdutoController"}).
	when("/produtos/:id", {templateUrl: "partials/produto.html", controller: "produtoController"}).
	when("/clientes", {templateUrl: "partials/clientes.html", controller: "clientesController"}).
	when("/clientes/novo", {templateUrl: "partials/cliente.html", controller: "novoClienteController"}).
	when("/clientes/:id", {templateUrl: "partials/cliente.html", controller: "clienteController"}).
	when("/pedidos", {templateUrl: "partials/pedidos.html", controller: "pedidosController"}).
	when("/pedidos/novo", {templateUrl: "partials/pedido.html", controller: "novoPedidoController"}).
	when("/pedidos/:id", {templateUrl: "partials/pedido.html", controller: "pedidoController"}).
	otherwise({redirectTo: '/'});
}]);