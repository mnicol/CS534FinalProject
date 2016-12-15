clear;
%Get our Source
Source = im2double(imread('source.jpg'));
%Get our Target
Target = im2double(imread('target.jpg'));
%Get our Mask
Mask = imbinarize(imread('mask.jpg'));
%For some reason white is 0 and black is 1
%Mask = imcomplement(Mask);
%Calculate Blend
result = Blender(Source, Target, Mask);
result = result;