# Import required libraries
import cv2
import numpy as np
from matplotlib import pyplot as plt

# Gets pixel location of horizontal and vertial line that make up the chess board
# For the function to succeed, the board should be empty
def getChessboardLines(empty_board):
    # Find chessboard corners
    harris = cv2.cornerHarris(empty_board, 2, 3, 0.04)
    norm = harris
    cv2.normalize(harris, norm, 0, 255, cv2.NORM_MINMAX)
    absoluteValue = cv2.convertScaleAbs(norm)

    # Create blank image and mark corners
    rows,cols = empty_board.shape
    blackbg = np.zeros(empty_board.shape, empty_board.dtype)
    blackbg[absoluteValue>0.30*absoluteValue.max()]=[255]

    # Gather data points
    vdata = []
    hdata = []
    for i in xrange(rows):
        for j in xrange(cols):
            if  blackbg[i,j] >= 255:
                vdata.append(i)
                hdata.append(j)

    # Sort vertical data points into groups
    maxgap = 15
    vdata.sort()
    vgroups = [[vdata[0]]]
    for x in vdata[1:]:
        if abs(x - vgroups[-1][-1] <= maxgap):
           vgroups[-1].append(x)
        else:
           vgroups.append([x])

    # Sort horizontal data points in groups
    hdata.sort()
    hgroups = [[hdata[0]]]
    for x in hdata[1:]:
        if abs(x - hgroups[-1][-1] <=maxgap):
            hgroups[-1].append(x)
        else:
            hgroups.append([x])

    # Make sure there are at least 9 groupings for each axis
    if (len(vgroups) < 9) or (len(hgroups) < 9):
        print "Error: incorrect number of grid lines found"

    # Pick 9 largest groupings for each axis
    hgroups.sort(key=len, reverse=True)
    filtered_hgroups = hgroups[:9]
    vgroups.sort(key=len, reverse=True)
    filtered_vgroups = vgroups[:9]

    # Get average location for each line
    vlines = [sum(x)/len(x) for x in filtered_vgroups]
    hlines = [sum(x)/len(x) for x in filtered_hgroups]

    # Draw lines
    for x in hlines:
        cv2.line(blackbg, (x,0), (x,rows), 255, 2)
    for y in vlines:
        cv2.line(blackbg, (0,y), (cols,y), 255, 2)

    return vlines,hlines, harris, blackbg


# Returns a list of 64 histograms, one for each square of the chess board
def get_all_histograms(vlines, hlines, img):
    # Seperate into 64 images, one for each square on chessboard
    squareImages = []
    for x in range(len(vlines[:-1])):
        for y in range(len(hlines[:-1])):
            squareImages.append(img[vlines[x]:vlines[x+1], hlines[y]:hlines[y+1]])

    # Crop the square images by 13% to account for error in cutouts
    im_cropped = []
    for im in squareImages:
        imrows, imcols = im.shape
        xmargin = int(0.13*imcols)
        ymargin = int(0.13*imrows)
        im_cropped.append(im[xmargin:imcols-xmargin, ymargin:imrows-ymargin])

    # Take histogram of each square image
    im_hist = []
    for im in im_cropped:
        im_hist.append(cv2.calcHist([im], [0], None, [256], [0,256]))

    return im_hist


# Compare two lists of histograms to find the two with least correlation
def compare_histograms(hist1, hist2):
    # Get list of correlation values
    correlation_values = []
    for x in range(min(len(hist1),len(hist2))):
        correlation_values.append(cv2.compareHist(hist1[x], hist2[x], 2))

    # Get index of histogram with least correlation
    index1 = correlation_values.index(min(correlation_values))

    correlation_values[index1] = max(correlation_values)
    index2 = correlation_values.index(min(correlation_values))

    return index1, index2



# ------ MAIN -------

# Load image as grayscale
img = cv2.imread('1.jpg', 0)
img2 = cv2.imread('2.jpg', 0)
img3 = cv2.imread('3.jpg', 0)

vlines, hlines, harris, blackbg = getChessboardLines(img)
print vlines
print hlines

old_hist = get_all_histograms(vlines, hlines, img2)
new_hist = get_all_histograms(vlines, hlines, img3)
i1, i2 = compare_histograms(old_hist, new_hist)
print i1
print i2


# Display images
plt.subplot(1,3,1),plt.imshow(img, 'gray', vmin=0, vmax=255)
plt.title('Original'), plt.xticks([]), plt.yticks([])
plt.subplot(1,3,2),plt.imshow(harris, 'gray', vmin=0, vmax=255)
plt.title('Harris'), plt.xticks([]), plt.yticks([])
plt.subplot(1,3,3),plt.imshow(blackbg, 'gray', vmin=0, vmax=255)
plt.title('Blackbg'), plt.xticks([]), plt.yticks([])
# plt.subplot(3,3,4),plt.plot(old_hist[35])
# plt.title('Old Histogram'), plt.xticks([]), plt.yticks([])
# plt.subplot(3,3,5),plt.plot(old_hist[i1])
# plt.title(''), plt.xticks([]), plt.yticks([])
# plt.subplot(3,3,6),plt.plot(old_hist[i2])
# plt.title(''), plt.xticks([]), plt.yticks([])
# plt.subplot(3,3,7),plt.plot(new_hist[35])
# plt.title('New Histogram'), plt.xticks([]), plt.yticks([])
# plt.subplot(3,3,8),plt.plot(new_hist[i1])
# plt.title(''), plt.xticks([]), plt.yticks([])
# plt.subplot(3,3,9),plt.plot(new_hist[i2])
# plt.title(''), plt.xticks([]), plt.yticks([])
plt.show()

