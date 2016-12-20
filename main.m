
%img1 = source image
%img2 = target image
%top, bottom, left, right = boundaries of selected object
%targetrow, targetcol = The coordinates of the upper left corner of the box
%in our target image.
function [ out ] = main( img1, img2, top, bottom, left, right, targetrow, targetcol )
%MAIN Summary of this function goes here
%   Detailed explanation goes here
    %img = imread('/Users/bradmiller/Documents/MATLAB/534_final/Fire-Hydrant-Installation-Schererville-IN-300x200.jpg');
    %img = imread('tiger.jpg');
    
    [m,n,k] = size(img1);
    backgroundBox = zeros(m,n,k);
    for i=left:right
       for j=top:bottom
           backgroundBox(j,i,:) = 1;
       end
    end
    
    JND = 3.6;
    
    [img_cut, img_background] = kMeansCut(img1, backgroundBox, JND);
    
    fullmask = imbinarize(rgb2gray(img_cut));
    
    sourcesection = img1(top:bottom, left:right, :);
    targetsection = img2(top + targetrow:bottom + targetrow, left + targetcol:right + targetcol, :);
    masksection = fullmask(top:bottom, left:right);
    blendedsection = Blender(sourcesection, targetsection, masksection);
    out = im2double(img2);
    out(top + targetrow:bottom + targetrow, left + targetcol:right + targetcol, :) = blendedsection;
end

