$(document).ready ->
  board = new Board 6, 7, (i, j) ->
    sendTurn j

  board.eachTile (tile) ->
    tile.addClass("btn")
    tile.addClass("ttt-tile")
  
  $('#cfcontainer').append(board.toHTML())

  $(window).resize ->
    reset()

  socket.bind "gamestate", (data) ->
    renderBoard data.board

  # Send a turn through the socket
  sendTurn = (col) ->
    socket.send {kind: 'move', data: col}

  # Draw the board from the specified board state
  renderBoard = (jsonBoard) ->
    board.eachTile (tile, i, j) ->
      style = switch jsonBoard[j][5-i]
        when 0 then "btn-success"
        when 1 then "btn-primary"
        else ""
      tile.addClass(style)

  reset = () ->
    top = $('#cfcontainer').position().top
    height = $(window).height() - (top + 10)
    $('#cfcontainer').height(height)
    board.reset()
  
  reset()

