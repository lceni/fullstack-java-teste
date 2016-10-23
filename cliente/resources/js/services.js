angular.module('FullstackJavaTesteApp.services', [])
.factory('produtosAPIservice', function($http) {
	var produtosAPI = {};
	
	produtosAPI.getProdutos = function(id) {
		return $http({
			method : 'GET',
			url : 'http://localhost:8080/produtos'
		});
	}
	
	produtosAPI.criar = function(doc) {
		return $http({
			method : 'POST',
			url : 'http://localhost:8080/produtos',
			data : doc
		});
	}
	
	produtosAPI.salvar = function(id, doc) {
		return $http({
			method : 'PUT',
			url : 'http://localhost:8080/produtos/' + id,
			data : doc
		});
	}
	
	produtosAPI.excluir = function(id) {
		return $http({
			method : 'DELETE',
			url : 'http://localhost:8080/produtos/' + id
		});
	}
	
	produtosAPI.recuperar = function(id) {
		return $http({
			method : 'GET',
			url : 'http://localhost:8080/produtos/' + id
		});
	}

	return produtosAPI;
}).factory('clientesAPIservice', function($http) {
	var clientesAPI = {};
	
	clientesAPI.getClientes = function(id) {
		return $http({
			method : 'GET',
			url : 'http://localhost:8080/clientes'
		});
	}
	
	clientesAPI.criar = function(doc) {
		return $http({
			method : 'POST',
			url : 'http://localhost:8080/clientes',
			data : doc
		});
	}
	
	clientesAPI.salvar = function(id, doc) {
		return $http({
			method : 'PUT',
			url : 'http://localhost:8080/clientes/' + id,
			data : doc
		});
	}
	
	clientesAPI.excluir = function(id) {
		return $http({
			method : 'DELETE',
			url : 'http://localhost:8080/clientes/' + id
		});
	}
	
	clientesAPI.recuperar = function(id) {
		return $http({
			method : 'GET',
			url : 'http://localhost:8080/clientes/' + id
		});
	}

	return clientesAPI;
}).factory('pedidosAPIservice', function($http) {
	var pedidosAPI = {};
	
	pedidosAPI.getPedidos = function(id) {
		return $http({
			method : 'GET',
			url : 'http://localhost:8080/pedidos'
		});
	}
	
	pedidosAPI.criar = function(doc) {
		return $http({
			method : 'POST',
			url : 'http://localhost:8080/pedidos',
			data : doc
		});
	}
	
	pedidosAPI.salvar = function(id, doc) {
		return $http({
			method : 'PUT',
			url : 'http://localhost:8080/pedidos/' + id,
			data : doc
		});
	}
	
	pedidosAPI.excluir = function(id) {
		return $http({
			method : 'DELETE',
			url : 'http://localhost:8080/pedidos/' + id
		});
	}
	
	pedidosAPI.recuperar = function(id) {
		return $http({
			method : 'GET',
			url : 'http://localhost:8080/pedidos/' + id
		});
	}

	return pedidosAPI;
}).factory('dateAPIservice', function() {
	var dateAPI = {};

	dateAPI.criarData = function (str) {
		var parts = str.split("/");
		var dt = new Date(parseInt(parts[2], 10),
		                  parseInt(parts[1], 10) - 1,
		                  parseInt(parts[0], 10));
		
		return dt.toISOString();
	}
	
	dateAPI.formatarData = function (inputFormat) {
		function pad(s) { return (s < 10) ? '0' + s : s; }
		var d = new Date(inputFormat);
		return [pad(d.getDate()), pad(d.getMonth()+1), d.getFullYear()].join('/');
	}

	return dateAPI;
});