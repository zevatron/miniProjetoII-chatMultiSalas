$(function() {
		var params = (new URL(document.location)).searchParams;

//		checkCookie();
		
		var sala = params.get("sala");
		var user = params.get("nickname");

		var socket = new WebSocket("ws://" + document.location.host
				+ "/chatMultiSalas/" + sala + "/" + user);
		
//		setCookie("chatMultiSalas",sala);
		
		atualizarNome(user);
		$('#navBarTitle').text('ChatMultiSalas - ' + sala);

		function atualizarNome(nome) {
			if (socket) {
				user = nome;
				$('#navbarDropdownMenuLinkUser').text(nome);
			}			
		}
		
		function setCookie(cname, cvalue) {
		    document.cookie = cname + "=" + cvalue + ";" ;
		}
		
		function getCookie(cname) {
		    var name = cname + "=";
		    var ca = document.cookie.split(';');
		    for(var i = 0; i < ca.length; i++) {
		        var c = ca[i];
		        while (c.charAt(0) == ' ') {
		            c = c.substring(1);
		        }
		        if (c.indexOf(name) == 0) {
		            return c.substring(name.length, c.length);
		        }
		    }
		    return "";
		}

		function checkCookie() {
		    var user = getCookie("chatMultiSalas");
		    if (user != "") {
		        alert("Você já participa da sala: " + user 
		        		+ ". Não é permitido participar de mais de uma sala ao mesmo tempo.");
		        window.history.back();
		    } 
		}
		
		$('#listUsers').click(function () {
			$('#m').focus();
		});
		

		$('#formMessage').submit(function() {
			if ($('#m').val().length > 0) {
				if($('#listUsers').val()[0] != "Todos"){
					var msg = "send -u " + $('#listUsers').val()[0] + " " + $('#m').val();
				}else{
					var msg = "send " + $('#m').val();					
				}
				socket.send(msg);
				$('#m').val('');
			}
			return false;
		});
		
		$('#formRename').submit(function() {
			if ($('#rename').val().length > 0) {
				var msg = "rename " + $('#rename').val();
				atualizarNome($('#rename').val());
				socket.send(msg);
				$('#rename').val('');
				$('#exampleModal').modal('toggle');
			}
			return false;
		});
		

		socket.onmessage = function(event) {
			msg = event.data;
			if (msg.split(' ')[0] == 'user-list') {
				users = msg.slice(11, -1).split(',');
				$('#listUsers').empty();
				$('#listUsers').append($('<option>').text('Todos')
						.addClass('list-group-item list-group-item-action')
						.attr('selected','selected'));
				for (i = 0; i < users.length; i++) {
					$('#listUsers').append($('<option>')
							.text(users[i]).addClass('list-group-item list-group-item-action')
							.val(users[i][0]==' ' ? users[i].slice(1) : users[i] ));
				}
			}else if(msg.split(' ')[0] == 'rename'){
				atualizarNome(msg.slice(7, -1));
			} else {
				$('#messages').append($('<li>').text(msg));
			}
		};
		
		socket.onclose = function (event) {
			document.cookie = "chatMultiSalas=; expires=Thu, 01 Jan 1970 00:00:00 UTC;";
		};
		

	});