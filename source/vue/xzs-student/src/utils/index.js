
export function formatSeconds (theTime) {
  let theTime1 = 0
  let theTime2 = 0
  if (theTime > 60) {
    theTime1 = parseInt(theTime / 60)
    theTime = parseInt(theTime % 60)
    if (theTime1 > 60) {
      theTime2 = parseInt(theTime1 / 60)
      theTime1 = parseInt(theTime1 % 60)
    }
  }
  let result = '' + parseInt(theTime) + ' s'
  if (theTime1 > 0) {
    result = '' + parseInt(theTime1) + 'm' + result
  }
  if (theTime2 > 0) {
    result = '' + parseInt(theTime2) + 'h' + result
  }
  return result
}
