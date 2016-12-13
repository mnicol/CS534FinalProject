clear;
%Get our Source
Source = imread('testsource.jpg');
%Get our Target
Target = imread('testtarget.jpg');
%Get our Mask
Mask = imbinarize(rgb2gray(imread('testmask.jpg')));
%For some reason white is 0 and black is 1
Mask = imcomplement(Mask);
%Calculate Blend
result = Blender(Source, Target, Mask);
result = result;