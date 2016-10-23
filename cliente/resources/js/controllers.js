angular.module('FullstackJavaTesteApp.controllers', []).
controller('mainController', function($scope, $location) {
	$scope.getClass = function (path) {
	  return ($location.path().substr(0, path.length) === path) ? 'active' : '';
	}
	
	$scope.go = function ( path ) {
	  $location.path( path );
	};
}).
controller('produtosController', function($scope, produtosAPIservice) {
	$scope.produtoFilter = null;
    $scope.produtosList = [];
    
    $scope.searchFilter = function (produto) {
        var keyword = new RegExp($scope.produtoFilter, 'i');
        return !$scope.produtoFilter || keyword.test(produto.codigo) || keyword.test(produto.descricao);
    };

    produtosAPIservice.getProdutos().success(function (response) {
    	$scope.produtosList = response;
    }).error(function(data, status, headers, config) {
    	console.log(status);
    });
}).
controller('novoProdutoController', function($scope, $routeParams, $location, produtosAPIservice) {
	$scope.novo = true;
	
	$scope.salvar = function() {
		var doc = {};
		doc.codigo = $scope.codigo;
		doc.descricao = $scope.descricao;
		doc.valor = $scope.valor;
		
		produtosAPIservice.criar(doc).success(function (response) {
			$location.path('/produtos');
		}).error(function(data, status) {
			console.log(data);
		});
	};
}).
controller('produtoController', function($scope, $routeParams, $location, produtosAPIservice) {
  $scope.id = $routeParams.id;
  
  produtosAPIservice.recuperar($scope.id).success(function (response) {
	  $scope.codigo = response.codigo;
	  $scope.descricao = response.descricao;
	  $scope.valor = response.valor;
	}).error(function(data, status) {
		console.log(data);
	});
  
  $scope.salvar = function() {
		var doc = {};
		doc.codigo = $scope.codigo;
		doc.descricao = $scope.descricao;
		doc.valor = $scope.valor;
		
		produtosAPIservice.salvar($scope.id, doc).success(function (response) {
			$location.path('/produtos');
		}).error(function(data, status) {
			console.log(data);
		});
	};
	
	$scope.excluir = function() {
		produtosAPIservice.excluir($scope.id).success(function (response) {
			$location.path('/produtos');
		}).error(function(data, status) {
			console.log(data);
		});
	};
}).
controller('homeController', function($scope) {
	// OK
}).
controller('clientesController', function($scope, clientesAPIservice) {
	$scope.clienteFilter = null;
    $scope.clientesList = [];
    
    $scope.searchFilter = function (cliente) {
        var keyword = new RegExp($scope.clienteFilter, 'i');
        return !$scope.clienteFilter || keyword.test(cliente.telefone) || keyword.test(cliente.email) || keyword.test(cliente.nome) || keyword.test(cliente.razaoSocial);
    };

    clientesAPIservice.getClientes().success(function (response) {
    	$scope.clientesList = response;
    }).error(function(data, status, headers, config) {
    	console.log(status);
    });
}).
controller('novoClienteController', function($scope, $routeParams, $location, clientesAPIservice) {
	$scope.novo = true;
	$scope.type = "pessoaFisica";
	
	$scope.trocar = function(type) {
		$scope.type = type;
	};
		
	$scope.salvar = function() {
		var doc = {};
		doc.type = $scope.type;
		doc.nome = $scope.nome;
		doc.cpf = $scope.cpf;
		doc.razaoSocial = $scope.razaoSocial;
		doc.cnpj = $scope.cnpj;
		doc.telefone = $scope.telefone;
		doc.email = $scope.email;
		
		clientesAPIservice.criar(doc).success(function (response) {
			$location.path('/clientes');
		}).error(function(data, status) {
			console.log(data);
		});
	};
}).
controller('clienteController', function($scope, $routeParams, $location, clientesAPIservice) {
  $scope.id = $routeParams.id;
  $scope.type = "pessoaFisica";
	
	$scope.trocar = function(type) {
		$scope.type = type;
	};
		
  clientesAPIservice.recuperar($scope.id).success(function (response) {
	  $scope.type = response.type;
	  $scope.nome = response.nome;
	  $scope.cpf = response.cpf;
	  $scope.razaoSocial = response.razaoSocial;
	  $scope.cnpj = response.cnpj;
	  $scope.telefone = response.telefone;
	  $scope.email = response.email;
	}).error(function(data, status) {
		console.log(data);
	});
  
  $scope.salvar = function() {
		var doc = {};
		doc.type = $scope.type;
		doc.nome = $scope.nome;
		doc.cpf = $scope.cpf;
		doc.razaoSocial = $scope.razaoSocial;
		doc.cnpj = $scope.cnpj;
		doc.telefone = $scope.telefone;
		doc.email = $scope.email;
		
		clientesAPIservice.salvar($scope.id, doc).success(function (response) {
			$location.path('/clientes');
		}).error(function(data, status) {
			console.log(data);
		});
	};
	
	$scope.excluir = function() {
		clientesAPIservice.excluir($scope.id).success(function (response) {
			$location.path('/clientes');
		}).error(function(data, status) {
			console.log(data);
		});
	};
}).
controller('pedidosController', function($scope, pedidosAPIservice, dateAPIservice) {
	$scope.dateAPIservice = dateAPIservice;
	$scope.pedidosFilter = null;
    $scope.pedidosList = [];
    
    $scope.searchFilter = function (pedido) {
        var keyword = new RegExp($scope.pedidosFilter, 'i');
        return !$scope.pedidosFilter || keyword.test(pedido.cliente.nome) || keyword.test(pedido.cliente.razaoSocial);
    };

    pedidosAPIservice.getPedidos().success(function (response) {
    	$scope.pedidosList = response;
    }).error(function(data, status, headers, config) {
    	console.log(status);
    });
}).
controller('novoPedidoController', function($scope, $routeParams, $location, pedidosAPIservice, clientesAPIservice, produtosAPIservice, dateAPIservice) {
	$scope.novo = true;
	$scope.cliente = null;
	$scope.clientes = null;
	$scope.produtos = [];
	$scope.produtosList = [];
	
	 function getQuantidade(produto) {
		var count = 0;
		for (x in $scope.produtos) {
			if (produto.id == $scope.produtos[x].id) {
				count++;
			}
		}
		
		return count;
	};
	
	$scope.adicionar = function(produto) {
		$scope.produtos.push(produto);
		produto.quantidade = getQuantidade(produto);
	};
	
	$scope.remover = function(produto) {
		if ($scope.produtos.indexOf(produto) >= 0) {
			$scope.produtos.splice($scope.produtos.indexOf(produto), 1);
		}
		produto.quantidade = getQuantidade(produto);
	};
	
	produtosAPIservice.getProdutos().success(
			function(response) {
				$scope.produtosList = response;
				
				for (x in $scope.produtosList) {
					var produto = $scope.produtosList[x];
					produto.quantidade = getQuantidade(produto);
				}
			}).error(function(data, status, headers, config) {
		console.log(status);
	});
	
	clientesAPIservice.getClientes().success(function(response) {
		$scope.clientes = response;
	});
	
	$scope.salvar = function() {
		var doc = {};
		doc.numero = $scope.numero;
		doc.dataEmissao = dateAPIservice.criarData($scope.dataEmissao);
		doc.cliente = $scope.cliente;
		doc.produtos = $scope.produtos;
	
		pedidosAPIservice.criar(doc).success(
				function(response) {
					$location.path('/pedidos');
					}).error(function(data, status) {
				console.log(data);
			});
		};
}).
controller('pedidoController', function($scope, $routeParams, $location, pedidosAPIservice, clientesAPIservice, produtosAPIservice, dateAPIservice) {
	$scope.id = $routeParams.id;
	$scope.cliente = null;
	$scope.clientes = null;
	$scope.produtos = [];
	$scope.produtosList = [];
	
	 function getQuantidade(produto) {
		var count = 0;
		for (x in $scope.produtos) {
			if (produto.id == $scope.produtos[x].id) {
				count++;
			}
		}
		
		return count;
	};
	
	$scope.adicionar = function(produto) {
		$scope.produtos.push(produto);
		produto.quantidade = getQuantidade(produto);
	};
	
	$scope.remover = function(produto) {
		for (x in $scope.produtos) {
			if ($scope.produtos[x].id == produto.id) {
				$scope.produtos.splice(x, 1);
				break;
			}
		}
		produto.quantidade = getQuantidade(produto);
	};
	
	pedidosAPIservice.recuperar($scope.id).success(function (response) {
		  $scope.numero = response.numero;
		  $scope.dataEmissao = dateAPIservice.formatarData(response.dataEmissao);
		  $scope.cliente = response.cliente;
		  $scope.produtos = response.produtos;
		}).error(function(data, status) {
			console.log(data);
		});
	
	produtosAPIservice.getProdutos().success(
			function(response) {
				$scope.produtosList = response;
				
				for (x in $scope.produtosList) {
					var produto = $scope.produtosList[x];
					produto.quantidade = getQuantidade(produto);
				}
			}).error(function(data, status, headers, config) {
		console.log(status);
	});
	
	clientesAPIservice.getClientes().success(
	function(response) {
		$scope.clientes = response;
	});
	
	$scope.salvar = function() {
		var doc = {};
		doc.numero = $scope.numero;
		doc.dataEmissao = dateAPIservice.criarData($scope.dataEmissao);
		doc.cliente = $scope.cliente;
		doc.produtos = $scope.produtos;
	
		pedidosAPIservice.salvar($scope.id, doc).success(
				function(response) {
					$location.path('/pedidos');
					}).error(function(data, status) {
				console.log(data);
			});
		};
	
	$scope.excluir = function() {
		pedidosAPIservice.excluir($scope.id).success(function (response) {
			$location.path('/pedidos');
		}).error(function(data, status) {
			console.log(data);
		});
	};
});